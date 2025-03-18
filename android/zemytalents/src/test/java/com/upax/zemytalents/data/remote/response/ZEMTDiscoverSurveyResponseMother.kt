package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTBreakDiscoverResponse
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyDiscoverResponse
import com.upax.zemytalents.data.remote.responses.ZEMTGroupQuestionDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTDiscoverSurveyResponseMother {

    fun apply(
        breaks: List<ZEMTBreakDiscoverResponse> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTBreakResponseMother.random()
        },
        questionsGroups: List<ZEMTGroupQuestionDiscoverResponse> = ZEMTRandomValuesUtil
            .getRandomIntRange().map { ZEMTGroupQuestionResponseMother.random() }
    ): ZEMTSurveyDiscoverResponse {
        return ZEMTSurveyDiscoverResponse(
            breaks = breaks,
            questionsGroups = questionsGroups
        )
    }

    fun random() = apply()

}