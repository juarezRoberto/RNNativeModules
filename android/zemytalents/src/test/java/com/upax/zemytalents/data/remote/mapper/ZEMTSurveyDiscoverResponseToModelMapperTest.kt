package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTBreakResponseMother
import com.upax.zemytalents.data.remote.response.ZEMTDiscoverSurveyResponseMother
import com.upax.zemytalents.data.remote.response.ZEMTGroupQuestionResponseMother
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class ZEMTSurveyDiscoverResponseToModelMapperTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()
    private val mapper = ZEMTDiscoverSurveyResponseToModelMapperMother.random()
    private val discoverSurveyResponse = ZEMTDiscoverSurveyResponseMother.random()
    private val surveyId = ZEMTSurveyId("99")

    @Test
    fun `mapper should map survey id correctly`() = runTest {
        val survey = mapper.invoke(discoverSurveyResponse, surveyId)

        assertEquals(surveyId, survey.id)
    }

    @Test
    fun `mapper should map breaks correctly`() = runTest {
        val discoverSurveyResponse = discoverSurveyResponse.copy(
            breaks = listOf(
                ZEMTBreakResponseMother.random(),
                ZEMTBreakResponseMother.random(),
                ZEMTBreakResponseMother.random()
            )
        )
        val survey = mapper.invoke(discoverSurveyResponse, surveyId)

        assertEquals(3, survey.breaks.size)
    }

    @Test
    fun `mapper should map group questions correctly`() = runTest {
        val discoverSurveyResponse = discoverSurveyResponse.copy(
            questionsGroups = listOf(
                ZEMTGroupQuestionResponseMother.random(),
                ZEMTGroupQuestionResponseMother.random(),
                ZEMTGroupQuestionResponseMother.random(),
                ZEMTGroupQuestionResponseMother.random()
            )
        )
        val survey = mapper.invoke(discoverSurveyResponse, surveyId)

        assertEquals(4, survey.groupQuestions.size)
    }

}