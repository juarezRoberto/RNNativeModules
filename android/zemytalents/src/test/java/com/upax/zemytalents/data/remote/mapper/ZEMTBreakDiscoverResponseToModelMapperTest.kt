package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTBreakResponseMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTBreakDiscoverResponseToModelMapperTest {

    private val mapper = ZEMTBreakDiscoverResponseToModelMapper(ZEMTAttachmentDiscoverResponseToModelMapper())

    @Test
    fun `mapper should return empty list when breaks response is null`() {
        val breaks = mapper.invoke(null)

        assertTrue(breaks.isEmpty())
    }

    @Test
    fun `mapper should map breaks index group questions correctly`() {
        val breaksResponse = listOf(
            ZEMTBreakResponseMother.apply(indexQuestionGroup = 10),
            ZEMTBreakResponseMother.apply(indexQuestionGroup = 20),
            ZEMTBreakResponseMother.apply(indexQuestionGroup = 30)
        )


        val breaks = mapper.invoke(breaksResponse)

        val breaksIndexGroupQuestion = breaks.map { it.indexGroupQuestion.value }
        val breaksIndexGroupQuestionsExpected = breaksResponse.map { it.indexQuestionGroup }
        assertEquals(breaksIndexGroupQuestionsExpected, breaksIndexGroupQuestion)
    }


    @Test
    fun `mapper should map breaks text correctly`() {
        val breaksResponse = listOf(
            ZEMTBreakResponseMother.apply(text = "text 1"),
            ZEMTBreakResponseMother.apply(text = "text 2"),
            ZEMTBreakResponseMother.apply(text = "text 3")
        )

        val breaks = mapper.invoke(breaksResponse)

        val breaksText = breaks.map { it.text }
        val breaksTextExpected = breaksResponse.map { it.text }
        assertEquals(breaksTextExpected, breaksText)
    }

}