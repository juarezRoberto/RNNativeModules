package com.upax.zemytalents.data.remote.response

import com.upax.zemytalents.data.remote.responses.ZEMTAttachmentDiscoverResponse
import com.upax.zemytalents.data.remote.responses.ZEMTBreakDiscoverResponse
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTBreakResponseMother {

    fun apply(
        text: String = ZEMTRandomValuesUtil.getString(),
        indexQuestionGroup: Int = ZEMTRandomValuesUtil.getInt(),
        attachments: List<ZEMTAttachmentDiscoverResponse> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTAttachmentResponseMother.random() }
    ): ZEMTBreakDiscoverResponse {
        return ZEMTBreakDiscoverResponse(
            text = text,
            indexQuestionGroup = indexQuestionGroup,
            attachments = attachments
        )
    }

    fun random() = apply()

}