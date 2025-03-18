package com.upax.zemytalents.data.remote.requests

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass

@ZCSCEncryptClass
internal data class ZEMTSyncSurveyDiscoverAnswersRequest(
    @field:ZCSCEncrypt
    @SerializedName("collaborator_id") val collaboratorId: String,
    @SerializedName("latitude_init") val initialLatitude: String,
    @SerializedName("longitude_init") val initialLongitude: String,
    @SerializedName("latitude_end") val endLatitude: String,
    @SerializedName("longitude_end") val endLongitude: String,
    @SerializedName("date_init") val initialDate: String,
    @SerializedName("date_end") val endDate: String,
    @SerializedName("device") val device: String,
    @SerializedName("platform") val platform: String,
    @SerializedName("answer_questions") val answers: List<ZEMTAnswersSurveyDiscoverRequest>
)

@ZCSCEncryptClass
internal data class ZEMTAnswersSurveyDiscoverRequest(
    @SerializedName("id") val idQuestion: Int,
    @SerializedName("id_option_answer") val idAnswer: Int,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("date") val date: String
)