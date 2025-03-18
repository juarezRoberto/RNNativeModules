package com.upax.zemytalents.data.remote.responses.conversations

import com.google.gson.annotations.SerializedName
import com.upax.zemytalents.common.ZEMTQrFunctions.DATE_FORMAT
import com.upax.zemytalents.common.ZEMTQrFunctions.DATE_SERVICE_FORMAT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal data class ZEMTConversationsHistoryResponse(
    @SerializedName("conversations") val conversations: List<ZEMTConversationHistoryResponse>? = null
)

internal data class ZEMTConversationHistoryResponse(
    @SerializedName("boss_id") val bossId: String? = null,
    @SerializedName("phrase") val phrase: ZEMTPhraseHistoryResponse? = null,
    @SerializedName("realized") val realized: Boolean? = null,
    @SerializedName("start_date") private val _startDate: String? = null,
    @SerializedName("end_date") private val _endDate: String? = null,
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("device") val device: String? = null,
    @SerializedName("platform") val platform: String? = null
){
    val startDate: String get() = parseDate(_startDate.orEmpty())
    val endDate: String get() = parseDate(_endDate.orEmpty())

    private fun parseDate(date: String): String {
        val serviceFormatter = DateTimeFormatter.ofPattern(DATE_SERVICE_FORMAT)
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return LocalDateTime.parse(date, serviceFormatter).format(formatter)
    }
}

internal data class ZEMTPhraseHistoryResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("talent") val talent: ZEMTTalentHistoryResponse? = null,
    @SerializedName("conversation") val conversation: ZEMTConversationResponse? = null,
    @SerializedName("order") val order: Int? = null
)

internal data class ZEMTTalentHistoryResponse(
    @SerializedName("talent_id") val talentId: Int? = null,
    @SerializedName("description") val description: String? = null
)
