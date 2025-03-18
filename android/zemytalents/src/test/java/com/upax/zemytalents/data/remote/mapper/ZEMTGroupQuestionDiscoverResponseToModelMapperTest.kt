package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTGroupQuestionResponseMother
import com.upax.zemytalents.data.remote.response.ZEMTQuestionResponseMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTGroupQuestionDiscoverResponseToModelMapperTest {

    private val mapper = ZEMTGroupQuestionsDiscoverResponseToModelMapper(
        ZEMTQuestionDiscoverResponseToModelMapper(ZEMTAnswerOptionResponseToModelMapper())
    )

    @Test
    fun `mapper should return empty list when there is no group questions`() {
        val groupQuestions = mapper.invoke(null)

        assertTrue(groupQuestions.isEmpty())
    }

    @Test
    fun `mapper should map index group question correctly`() = runTest {
        val questionsGroupsResponse = listOf(
            ZEMTGroupQuestionResponseMother.apply(index = 7),
            ZEMTGroupQuestionResponseMother.apply(index = 8)
        )

        val questionsGroups = mapper.invoke(questionsGroupsResponse)

        val indexGroupQuestions = questionsGroups.map { it.index }
        val indexGroupQuestionsExpected = listOf(
            ZEMTGroupQuestionIndexDiscover(7), ZEMTGroupQuestionIndexDiscover(8)
        )
        assertEquals(indexGroupQuestionsExpected, indexGroupQuestions)
    }

    @Test
    fun `mapper should return empty list when group questions contains less than two questions`() {
        val groupQuestionsResponse = listOf(
            ZEMTGroupQuestionResponseMother.apply(
                questions = listOf(ZEMTQuestionResponseMother.random())
            ),
            ZEMTGroupQuestionResponseMother.apply(
                questions = listOf(ZEMTQuestionResponseMother.random())
            )
        )

        val groupQuestion = mapper.invoke(groupQuestionsResponse)

        assertTrue(groupQuestion.isEmpty())
    }

    @Test
    fun `mapper should map left question correctly`() = runTest {
        val leftQuestionResponse = ZEMTQuestionResponseMother.random()
        val questionsGroups = listOf(
            ZEMTGroupQuestionResponseMother.apply(
                questions = listOf(leftQuestionResponse, ZEMTQuestionResponseMother.random())
            )
        )

        val groupQuestions = mapper.invoke(questionsGroups)

        val leftQuestion = groupQuestions.first().leftQuestion
        assertEquals(leftQuestionResponse.id, leftQuestion.id.value)
    }

    @Test
    fun `mapper should map right question correctly`() = runTest {
        val rightQuestionResponse = ZEMTQuestionResponseMother.random()
        val questionsGroups = listOf(
            ZEMTGroupQuestionResponseMother.apply(
                questions = listOf(ZEMTQuestionResponseMother.random(), rightQuestionResponse)
            )
        )

        val groupQuestions = mapper.invoke(questionsGroups)

        val rightQuestion = groupQuestions.first().rightQuestion
        assertEquals(rightQuestionResponse.id, rightQuestion.id.value)
    }

}