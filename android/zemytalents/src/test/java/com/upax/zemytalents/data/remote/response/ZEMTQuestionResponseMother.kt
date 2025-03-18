package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTAnswerOptionDiscoverResponse
import com.upax.zemytalents.data.remote.responses.ZEMTQuestionDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTQuestionResponseMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        answerOptions: List<ZEMTAnswerOptionDiscoverResponse> = ZEMTRandomValuesUtil
            .getRandomIntRange().map { ZEMTAnswerOptionResponseMother.random() }
    ): ZEMTQuestionDiscoverResponse {
        return ZEMTQuestionDiscoverResponse(
            id = id,
            text = text,
            order = order,
            answerOptions = answerOptions
        )
    }

    fun random() = apply()

}