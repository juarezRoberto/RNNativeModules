package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.responses.ZEMTCaptionResponse
import com.upax.zemytalents.domain.models.ZEMTCaption

internal class ZEMTCaptionsResponseMapper :
    Function1<List<ZEMTCaptionResponse>?, List<ZEMTCaption>> {

    override fun invoke(captions: List<ZEMTCaptionResponse>?): List<ZEMTCaption> {
        return captions?.map {
            ZEMTCaption(
                captionId = it.captionId,
                sectionId = it.sectionId,
                sectionKey = it.sectionKey,
                value = it.value
            )
        } ?: emptyList()
    }
}