package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerDiscoverToEntityMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTQuestionDiscoverToEntityMapper
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscoverMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscoverMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionDiscoverMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTSurveyDiscoverMother
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
internal class ZEMTSurveyDiscoverRegistrarTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    private fun getSurveyDiscoverRegistrar(): ZEMTSurveyDiscoverRegistrar {
        return ZEMTSurveyDiscoverRegistrar(
            surveyDao = databaseTestRule.surveyDao(),
            questionDao = databaseTestRule.questionDao(),
            answerOptionDao = databaseTestRule.answerOptionDao(),
            breaksDao = databaseTestRule.breakDao(),
            questionToEntityMapper = ZEMTQuestionDiscoverToEntityMapper(),
            answerToEntityMapper = ZEMTAnswerDiscoverToEntityMapper()
        )
    }

    @Test
    fun `SurveyDiscoverRegistrar should update group question index to 1`() = runTest {
        databaseTestRule.surveyDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(surveyId = 1, groupQuestionIndex = 0)
        )
        val surveyDiscover = ZEMTSurveyDiscoverMother.random()

        getSurveyDiscoverRegistrar().invoke(surveyDiscover)

        val newGroupQuestionIndex = databaseTestRule.surveyDao().getSurvey()?.groupQuestionIndex
        assertTrue(newGroupQuestionIndex == 1)
    }

    @Test
    fun `SurveyDiscoverRegistrar should save all questions`() = runTest {
        val totalGroupQuestions = 11
        val surveyDiscover = ZEMTSurveyDiscoverMother.apply(
            groupQuestions = ZEMTRandomValuesUtil.getRandomIntRange(1, totalGroupQuestions).map {
                ZEMTGroupQuestionsDiscoverMother.apply(
                    index = ZEMTGroupQuestionIndexDiscover(it)
                )
            }
        )

        getSurveyDiscoverRegistrar().invoke(surveyDiscover)

        val groupQuestionsInDatabase = databaseTestRule.questionDao().getTotalGroupQuestions()
        assertEquals(totalGroupQuestions, groupQuestionsInDatabase)
    }

    @Test
    fun `SurveyDiscoverRegistrar should save all answers`() = runTest {
        val totalGroupQuestions = 11
        val surveyDiscover = ZEMTSurveyDiscoverMother.apply(
            groupQuestions = ZEMTRandomValuesUtil.getRandomIntRange(1, totalGroupQuestions).map {
                ZEMTGroupQuestionsDiscoverMother.apply(
                    leftQuestion = ZEMTQuestionDiscoverMother.apply(
                        answers = ZEMTRandomValuesUtil.getRandomIntRange(1, 3).map {
                            ZEMTAnswerOptionDiscoverMother.apply(
                                id = ZEMTAnswerId(UUID.randomUUID().hashCode())
                            )
                        }
                    ),
                    rightQuestion = ZEMTQuestionDiscoverMother.apply(
                        answers = ZEMTRandomValuesUtil.getRandomIntRange(1, 3).map {
                            ZEMTAnswerOptionDiscoverMother.apply(
                                id = ZEMTAnswerId(UUID.randomUUID().hashCode())
                            )
                        }
                    )
                )
            }
        )

        getSurveyDiscoverRegistrar().invoke(surveyDiscover)

        val answersInDatabase = databaseTestRule.answerOptionDao().getTotalAnswers()
        val answersPerQuestions = 3
        val totalAnswersExpected = totalGroupQuestions * (2 * answersPerQuestions)
        assertEquals(totalAnswersExpected, answersInDatabase)
    }

}