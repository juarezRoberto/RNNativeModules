package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.responses.ZEMTSurveyDiscoverResponse
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTSurveyDiscover

internal class ZEMTSurveyDiscoverResponseToModelMapper(
    private val breakResponseToModelMapper: ZEMTBreakDiscoverResponseToModelMapper,
    private val groupQuestionsResponseToModelMapper: ZEMTGroupQuestionsDiscoverResponseToModelMapper
) {

    fun invoke(
        surveyResponse: ZEMTSurveyDiscoverResponse,
        surveyId: ZEMTSurveyId
    ): ZEMTSurveyDiscover {
        return ZEMTSurveyDiscover(
            id = surveyId,
            breaks = breakResponseToModelMapper.invoke(surveyResponse.breaks),
            groupQuestions = groupQuestionsResponseToModelMapper.invoke(
                surveyResponse.questionsGroups
            )
        )
    }

}