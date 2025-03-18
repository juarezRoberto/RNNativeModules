package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName

internal data class ZEMTGetTalentsResponseWrapper(
    @SerializedName("talents") val talents: ZEMTGetTalentsResponse? = null
)

internal data class ZEMTGetTalentsResponse(
    @SerializedName("dominant") val dominant: List<ZEMTTalentResponse>? = null,
    @SerializedName("non_dominant") val notDominant: List<ZEMTTalentResponse>? = null,
)

internal data class ZEMTTalentResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("order") val order: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("attachments") val attachments: List<ZEMTTalentAttachmentResponse>? = null,
)

internal data class ZEMTTalentAttachmentResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("order") val order: Int? = null,
)