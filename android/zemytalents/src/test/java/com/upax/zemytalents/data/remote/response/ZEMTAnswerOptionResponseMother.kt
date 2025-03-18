package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTAnswerOptionDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAnswerOptionResponseMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        value: String = ZEMTRandomValuesUtil.getInt().toString()
    ): ZEMTAnswerOptionDiscoverResponse {
        return ZEMTAnswerOptionDiscoverResponse(
            id = id,
            text = text,
            order = order,
            value = value
        )
    }

    fun random() = apply()

}