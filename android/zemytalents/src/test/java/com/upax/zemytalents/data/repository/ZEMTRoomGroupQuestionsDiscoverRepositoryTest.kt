package com.upax.zemytalents.data.repository

import app.cash.turbine.test
import com.upax.zemytalents.data.local.database.entity.ZEMTAnswerSavedEntityMother
import com.upax.zemytalents.data.local.database.entity.ZEMTQuestionEntityMother
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.data.local.database.mapper.ZEMTListQuestionWithAnswersEntityToModelMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTQuestionWithAnswersEntityToModelMapper
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomGroupQuestionsDiscoverRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    private fun getRepository(): ZEMTRoomGroupQuestionsDiscoverRepository {
        return ZEMTRoomGroupQuestionsDiscoverRepository(
            surveyDao = databaseTestRule.surveyDao(),
            questionDao = databaseTestRule.questionDao(),
            answerSavedDao = databaseTestRule.answerSavedDao(),
            listQuestionWithAnswersToModelMapper = ZEMTListQuestionWithAnswersEntityToModelMapper(
                ZEMTQuestionWithAnswersEntityToModelMapper()
            )
        )
    }

    @Test
    fun `totalGroupQuestionsAnswered flow returns value correctly from database`() = runTest {
        val answerSavedDao = databaseTestRule.answerSavedDao()
        val answers = listOf(
            ZEMTAnswerSavedEntityMother.apply(answerOptionId = 1),
            ZEMTAnswerSavedEntityMother.apply(answerOptionId = 2),
            ZEMTAnswerSavedEntityMother.apply(answerOptionId = 3),
        )
        answers.forEach { answerSavedDao.insert(it) }

        getRepository().totalGroupQuestionsAnswered.test {
            assertEquals(answers.size, awaitItem())
        }
    }

    @Test
    fun `totalGroupQuestions returns value correctly from database`() = runTest {
        val totalQuestions = 100
        databaseTestRule.populateDatabase(
            initialGroupQuestionsIndex = 0,
            totalQuestions = totalQuestions
        )

        val totalGroupQuestionsExpected = (totalQuestions / 2)
        assertEquals(totalGroupQuestionsExpected, getRepository().getTotalGroupQuestions())
    }

    @Test
    fun `when updateGroupQuestionIndex is called then getCurrentGroupQuestionsIndex returns new value`() =
        runTest {
            val initialGroupSortProgress = 10
            databaseTestRule.populateDatabase(initialGroupQuestionsIndex = initialGroupSortProgress)
            val repository = getRepository()

            repository.updateGroupQuestionIndex(initialGroupSortProgress + 1)
            assertEquals(
                initialGroupSortProgress + 1,
                databaseTestRule.surveyDao().getCurrentGroupQuestionIndex()
            )
        }

    @Test
    fun `currentGroupQuestions emit questions from current groupQuestionIndex`() = runTest {
        val questionDao = databaseTestRule.questionDao()
        val surveyId = 99
        val questions = mutableListOf<ZEMTQuestionDiscoverEntity>()
        (1..100 / 2).forEach { groupQuestionIndex ->
            repeat(2) {
                questions.add(
                    ZEMTQuestionDiscoverEntity(
                        questionId = Random.nextInt(),
                        text = "This is the question $it",
                        surveyId = surveyId,
                        order = it,
                        groupQuestionIndex = groupQuestionIndex,
                        lastSeen = 0
                    )
                )
            }
        }
        databaseTestRule.questionDao().insertAll(questions)
        databaseTestRule.surveyDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(surveyId, 10)
        )
        questionDao.insertAll(questions)
        getRepository().currentGroupQuestions.test {
            val groupQuestion = awaitItem()
            assertEquals(questions[18].questionId, groupQuestion!!.leftQuestion.id.value)
            assertEquals(questions[19].questionId, groupQuestion.rightQuestion.id.value)
        }
    }

    @Test
    fun `currentGroupQuestions emit null when there is no questions`() = runTest {
        val surveyId = 99
        databaseTestRule.surveyDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(surveyId, 1)
        )
        getRepository().currentGroupQuestions.test {
            assertEquals(null, awaitItem())
        }
    }

    @Test
    fun `currentGroupQuestions emit null when questions with same groupQuestionIndex are less than 2`() =
        runTest {
            val questionDao = databaseTestRule.questionDao()
            val surveyId = 99
            val questions = (0..100).mapNotNull {
                if (it == 0) return@mapNotNull null
                ZEMTQuestionEntityMother.apply(
                    questionId = it,
                    surveyId = surveyId,
                    groupQuestionIndex = it
                )
            }
            databaseTestRule.surveyDao().insertDiscoverSurvey(
                ZEMTSurveyDiscoverEntity(surveyId, 10)
            )
            questionDao.insertAll(questions)

            getRepository().currentGroupQuestions.test {
                assertEquals(null, awaitItem())
            }
        }

    @Test
    fun `getNextGroupQuestionIndex should return 1 when there is no questions answers`() =
        runTest {
            databaseTestRule.populateDatabase(totalQuestions = 10)
            val repository = getRepository()
            assertEquals(1, repository.getNextGroupQuestionIndex())
        }

    @Test
    fun `when collect currentGroupQuestions should update lastSeen field`() =
        runTest {
            databaseTestRule.populateDatabase(initialGroupQuestionsIndex = 1, totalQuestions = 10)
            val repository = getRepository()
            val groupQuestionFlow = databaseTestRule.questionDao().getCurrentGroupQuestions()

            val oldCurrentGroupQuestion = groupQuestionFlow.first()
            val oldLastSeenLeftQuestion = oldCurrentGroupQuestion?.get(0)?.question?.lastSeen!!
            val oldLastSeenRightQuestion = oldCurrentGroupQuestion[1].question.lastSeen

            repository.currentGroupQuestions.first()

            val newCurrentGroupQuestion = groupQuestionFlow.first()
            val newLastSeenLeftQuestion = newCurrentGroupQuestion?.get(0)?.question?.lastSeen!!
            val newLastSeenRightQuestion = newCurrentGroupQuestion[1].question.lastSeen

            assertTrue(newLastSeenLeftQuestion > oldLastSeenLeftQuestion)
            assertTrue(newLastSeenRightQuestion > oldLastSeenRightQuestion)
        }

}