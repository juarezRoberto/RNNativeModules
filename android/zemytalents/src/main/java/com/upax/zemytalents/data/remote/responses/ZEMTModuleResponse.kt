package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName

internal data class ZEMTModuleResponseWrapper(
    @SerializedName("response") val modules: List<ZEMTModuleResponse>? = null
)

internal data class ZEMTModuleResponse(
    @SerializedName("module_id") val moduleId: Int? = null,
    @SerializedName("survey_id") val surveyId: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("order") val order: Int? = null,
    @SerializedName("multimedia") val multimedia: List<ZEMTModuleMultimediaResponse>? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("is_complete") val isComplete: Boolean? = null
)

internal data class ZEMTModuleMultimediaResponse(
    @SerializedName("url") val url: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("order") val order: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("duration") val duration: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url_thumbnail") val urlThumbnail: String? = null
)