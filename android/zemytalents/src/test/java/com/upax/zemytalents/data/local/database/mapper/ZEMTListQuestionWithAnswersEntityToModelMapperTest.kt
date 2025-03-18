package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.ZEMTQuestionEntityMother
import com.upax.zemytalents.data.local.database.entity.ZEMTQuestionWithAnswersMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ZEMTListQuestionWithAnswersEntityToModelMapperTest {

    @Test
    fun `mapper should map group question correctly`() {
        val groupQuestionIndex = 10
        val mockQuestionToReturn = ZEMTQuestionMother.random()
        val questionWithAnswersEntityMapper = mockk<ZEMTQuestionWithAnswersEntityToModelMapper> {
            every { this@mockk(any()) } returns mockQuestionToReturn
        }
        val questionsWithAnswersEntity = listOf(
            ZEMTQuestionWithAnswersMother.apply(
                ZEMTQuestionEntityMother.apply(groupQuestionIndex = groupQuestionIndex)
            ),
            ZEMTQuestionWithAnswersMother.apply(
                ZEMTQuestionEntityMother.apply(groupQuestionIndex = groupQuestionIndex)
            )
        )
        val mapper = ZEMTListQuestionWithAnswersEntityToModelMapper(questionWithAnswersEntityMapper)

        val groupQuestion = mapper.invoke(questionsWithAnswersEntity)

        assertEquals(
            ZEMTGroupQuestionsDiscover(
                index = ZEMTGroupQuestionIndexDiscover(groupQuestionIndex),
                leftQuestion = mockQuestionToReturn,
                rightQuestion = mockQuestionToReturn
            ),
            groupQuestion
        )
    }

    @Test
    fun `mapper should return null when questions with answers are null`() {
        val questionWithAnswersEntityMapper = mockk<ZEMTQuestionWithAnswersEntityToModelMapper>()
        val mapper = ZEMTListQuestionWithAnswersEntityToModelMapper(questionWithAnswersEntityMapper)

        val groupQuestion = mapper.invoke(null)

        assertEquals(null, groupQuestion)
    }

    @Test
    fun `mapper should return null when questions are less than two`() {
        val questionWithAnswersEntityMapper = mockk<ZEMTQuestionWithAnswersEntityToModelMapper>()
        val questionsWithAnswersEntity = listOf(ZEMTQuestionWithAnswersMother.random())
        val mapper = ZEMTListQuestionWithAnswersEntityToModelMapper(questionWithAnswersEntityMapper)

        val groupQuestion = mapper.invoke(questionsWithAnswersEntity)

        assertEquals(null, groupQuestion)
    }

}