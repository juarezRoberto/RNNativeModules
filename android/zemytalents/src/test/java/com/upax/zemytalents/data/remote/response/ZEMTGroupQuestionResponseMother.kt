package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTGroupQuestionDiscoverResponse
import com.upax.zemytalents.data.remote.responses.ZEMTQuestionDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTGroupQuestionResponseMother {

    fun apply(
        index: Int = ZEMTRandomValuesUtil.getInt(),
        questions: List<ZEMTQuestionDiscoverResponse> = ZEMTRandomValuesUtil.getRandomIntRange(1, 2)
            .map {
                ZEMTQuestionResponseMother.random()
            }
    ): ZEMTGroupQuestionDiscoverResponse {
        return ZEMTGroupQuestionDiscoverResponse(
            index = index,
            questions = questions
        )
    }

    fun random() = apply()

}