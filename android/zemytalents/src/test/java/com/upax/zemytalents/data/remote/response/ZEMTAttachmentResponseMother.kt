package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTAttachmentDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAttachmentResponseMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        url: String = ZEMTRandomValuesUtil.getString(),
        name: String = ZEMTRandomValuesUtil.getString(),
        type: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        description: String = ZEMTRandomValuesUtil.getString(),
        ): ZEMTAttachmentDiscoverResponse {
        return ZEMTAttachmentDiscoverResponse(
            id = id,
            url = url,
            name = name,
            type = type,
            order = order,
            description
        )
    }

    fun random() = apply()

}