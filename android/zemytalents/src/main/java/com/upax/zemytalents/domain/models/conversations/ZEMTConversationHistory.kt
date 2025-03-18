package com.upax.zemytalents.domain.models.conversations

internal data class ZEMTConversationHistory(
    val bossId: String,
    val phrase: ZEMTPhraseHistory,
    val realized: Boolean,
    val startDate: String,
    val endDate: String,
    val comment: String,
    val device: String,
    val platform: String
)

internal data class ZEMTPhraseHistory(
    val id: Int,
    val description: String,
    val talent: ZEMTTalentHistory,
    val conversation: ZEMTConversation,
    val order: Int
)

internal data class ZEMTTalentHistory(
    val talentId: Int,
    val description: String
)
