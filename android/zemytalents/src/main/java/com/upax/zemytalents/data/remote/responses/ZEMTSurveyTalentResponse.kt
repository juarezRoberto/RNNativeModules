package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName

internal data class ZEMTSurveyTalentsWrapper(
    @SerializedName("talents") val talents: List<ZEMTSurveyTalentResponse>?,
)

internal data class ZEMTSurveyTalentResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("questions") val questions: List<ZEMTSurveyTalentQuestionResponse>?,
)

internal data class ZEMTSurveyTalentQuestionResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("order") val order: Int?,
    @SerializedName("header") val header: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("answer_options") val answerOptions: List<ZEMTSurveyTalentOptionResponse>?,
)

internal data class ZEMTSurveyTalentOptionResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("order") val order: Int?,
    @SerializedName("text") val text: String?,
    @SerializedName("value") val value: Int?,
)