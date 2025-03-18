package com.upax.zemytalents.data.remote.requests

import com.google.gson.annotations.SerializedName
import com.upax.zcservicecoordinator.expose.ZCSCEncrypt
import com.upax.zcservicecoordinator.expose.ZCSCEncryptClass
import com.upax.zemytalents.common.ZEMTQrFunctions.parseDateForService
import java.time.LocalDateTime

@ZCSCEncryptClass
internal data class ZEMTSaveConversationBodyRequest(
    @field:ZCSCEncrypt @SerializedName("boss_id") val bossId: String,
    @field:ZCSCEncrypt @SerializedName("collaborator_id") val collaboratorId: String,
    @SerializedName("phrase_id") val phraseId: String,
    @SerializedName("realized") val isRealized: Boolean = true,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String = LocalDateTime.now().parseDateForService(),
    @SerializedName("comment") val comment: String,
    @SerializedName("device") val device: String,
    @SerializedName("platform") val platform: String,
)
