package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTAnswerOptionResponseMother
import com.upax.zemytalents.data.remote.response.ZEMTQuestionResponseMother
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ZEMTQuestionDiscoverResponseToModelMapperTest {

    val mapper = ZEMTQuestionDiscoverResponseToModelMapper(ZEMTAnswerOptionResponseToModelMapper())

    @Test
    fun `mapper should map question correctly`() = runTest {
        val questionResponse = ZEMTQuestionResponseMother.random()

        val question = mapper.invoke(questionResponse)

        assertEquals(questionResponse.id ,question.id.value)
        assertEquals(questionResponse.text ,question.text)
        assertEquals(questionResponse.order ,question.order)
    }

    @Test
    fun `mapper should map answer correctly`() = runTest {
        val questionResponse = ZEMTQuestionResponseMother.apply(
            answerOptions = listOf(
                ZEMTAnswerOptionResponseMother.random(),
                ZEMTAnswerOptionResponseMother.random(),
                ZEMTAnswerOptionResponseMother.random()
            )
        )

        val question = mapper.invoke(questionResponse)

        assertEquals(3,question.answers.size)
    }

}