package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTAnswerOptionResponseMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ZEMTAnswerOptionDiscoverOptionResponseToModelMapperTest {

    private val mapper = ZEMTAnswerOptionResponseToModelMapper()

    @Test
    fun `mapper should map answer question correctly`() = runTest {
        val questionId = ZEMTQuestionId(3)
        val answer1 = ZEMTAnswerOptionResponseMother.random()
        val answer2 = ZEMTAnswerOptionResponseMother.random()
        val answersOptionResponse = listOf(answer1, answer2)

        val answers = mapper.invoke(answersOptionResponse, questionId)

        val answersExpected = listOf(
            ZEMTAnswerOptionDiscover(
                id = ZEMTAnswerId(answer1.id!!),
                questionId = questionId,
                order = answer1.order!!,
                text = answer1.text!!,
                value = answer1.value!!.toInt()
            ),
            ZEMTAnswerOptionDiscover(
                id = ZEMTAnswerId(answer2.id!!),
                questionId = questionId,
                order = answer2.order!!,
                text = answer2.text!!,
                value = answer2.value!!.toInt()
            )
        )
        assertEquals(answersExpected, answers)
    }

}