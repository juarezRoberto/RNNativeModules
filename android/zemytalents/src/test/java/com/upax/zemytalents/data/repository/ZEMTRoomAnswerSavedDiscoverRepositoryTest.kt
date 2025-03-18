package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.entity.ZEMTAnswerOptionEntityMother
import com.upax.zemytalents.data.local.database.entity.ZEMTAnswerSavedEntityMother
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerSavedDiscoverEntityToModelMapper
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomAnswerSavedDiscoverRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    private fun getRepository(): ZEMTRoomAnswerSavedDiscoverRepository {
        return ZEMTRoomAnswerSavedDiscoverRepository(
            answerSavedDao = databaseTestRule.answerSavedDao(),
            questionDao = databaseTestRule.questionDao(),
            answerOptionMapper = mockk(),
            answerSavedMapper = ZEMTAnswerSavedDiscoverEntityToModelMapper()
        )
    }


    @Test
    fun `getTotalNeutralAnswers should return total answers with value 0`() = runTest {
        listOf(
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 1),
            ZEMTAnswerSavedEntityMother.apply(value = 5, answerOptionId = 2),
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 3),
            ZEMTAnswerSavedEntityMother.apply(value = 3, answerOptionId = 4),
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 5),
            ZEMTAnswerSavedEntityMother.apply(value = 5, answerOptionId = 6),
        ).forEach { databaseTestRule.answerSavedDao().insert(it) }

        val totalNeutralAnswers = getRepository().getTotalNeutralAnswers()

        assertEquals(3, totalNeutralAnswers)
    }

    @Test
    fun `getAnswers should return all answers`() = runTest {
        val answersInDatabase = listOf(
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 1),
            ZEMTAnswerSavedEntityMother.apply(value = 5, answerOptionId = 2),
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 3),
            ZEMTAnswerSavedEntityMother.apply(value = 3, answerOptionId = 4),
            ZEMTAnswerSavedEntityMother.apply(value = 0, answerOptionId = 5),
            ZEMTAnswerSavedEntityMother.apply(value = 5, answerOptionId = 6),
        )
        answersInDatabase.forEachIndexed { index, it ->
            val answerOption = ZEMTAnswerOptionEntityMother.apply(answerOptionId = index)
            databaseTestRule.answerOptionDao().insert(answerOption)
            databaseTestRule.answerSavedDao().insert(
                it.copy(answerOptionId = answerOption.answerOptionId)
            )
        }

        val answers = getRepository().getAnswers()

        assertEquals(answersInDatabase.size, answers.size)
    }

}