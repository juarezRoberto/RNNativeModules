package com.upax.zemytalents.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCDecrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass

@ZCSCEncryptClass
internal data class ZEMTSurveyDiscoverResponse(
    @SerializedName("breaks") val breaks: List<ZEMTBreakDiscoverResponse?>?,
    @SerializedName("questions_groups") val questionsGroups: List<ZEMTGroupQuestionDiscoverResponse?>?
)

@ZCSCEncryptClass
internal data class ZEMTBreakDiscoverResponse(
    @SerializedName("text") val text: String?,
    @SerializedName("adjuntos") val attachments: List<ZEMTAttachmentDiscoverResponse?>?,
    @SerializedName("index_question_group") val indexQuestionGroup: Int?
)

@ZCSCEncryptClass
 internal data class ZEMTAttachmentDiscoverResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("url") val url: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: Int?,
    @SerializedName("order") val order: Int?,
    @SerializedName("description") val description: String?
)

@ZCSCEncryptClass
internal data class ZEMTGroupQuestionDiscoverResponse(
    @SerializedName("index") val index: Int?,
    @SerializedName("questions") val questions: List<ZEMTQuestionDiscoverResponse?>?
)

@ZCSCEncryptClass
internal data class ZEMTQuestionDiscoverResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("text") val text: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("answer_options") val answerOptions: List<ZEMTAnswerOptionDiscoverResponse?>?
)

@ZCSCEncryptClass
internal data class ZEMTAnswerOptionDiscoverResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("text") val text: String?,
    @SerializedName("order") val order: Int?,
    @field:ZCSCDecrypt
    @SerializedName("value") val value: String?
)