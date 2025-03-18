package com.upax.zemytalents.ui.shared.models

import androidx.annotation.DrawableRes
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTConversationData
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTPhraseData

internal data class ZEMTCheckGroupItem(
    val text: String,
    @DrawableRes val icon: Int,
    val id: String
) {
    fun toConversationData() = ZEMTConversationData(id, text)
    fun toPhraseData() = ZEMTPhraseData(id, text)
}