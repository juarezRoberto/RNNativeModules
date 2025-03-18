package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.entity.ZEMTAnswerOptionEntityMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomAnswerOptionDiscoverRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    @Test
    fun `getAnswerById return answer from database correctly`() = runTest {
        val answerEntity = ZEMTAnswerOptionEntityMother.apply(answerOptionId = 1)
        databaseTestRule.answerOptionDao().insertAll(
            listOf(
                ZEMTAnswerOptionEntityMother.apply(answerOptionId = 2),
                answerEntity,
                ZEMTAnswerOptionEntityMother.apply(answerOptionId = 3),
            )
        )
        val repository = ZEMTRoomAnswerOptionDiscoverRepository(databaseTestRule.answerOptionDao())

        val answer = repository.getAnswerById(ZEMTAnswerId(answerEntity.answerOptionId))

        val answerExpected = ZEMTAnswerOptionDiscover(
            id = ZEMTAnswerId(answerEntity.answerOptionId),
            questionId = ZEMTQuestionId(answerEntity.questionId),
            order = answerEntity.order,
            text = answerEntity.text,
            value = answerEntity.value
        )
        assertEquals(answerExpected, answer)
    }

}