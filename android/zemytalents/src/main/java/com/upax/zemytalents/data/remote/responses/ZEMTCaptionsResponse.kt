package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ZEMTCaptionsResponse(
    val captions: List<ZEMTCaptionResponse>
)

data class ZEMTCaptionResponse(
    @SerializedName("caption_id") val captionId: Int,
    @SerializedName("section_id") val sectionId: Int,
    @SerializedName("section_key") val sectionKey: String,
    @SerializedName("value") val value: String
)