package com.upax.zemytalents.domain.models.conversations

import androidx.annotation.DrawableRes
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

internal data class ZEMTConversation(
    val conversationId: String,
    val name: String,
    val description: String,
    val order: Int,
    @DrawableRes val icon: Int,
    val lottieUrl: String,
) {
    fun toCheckGroupItem(): ZEMTCheckGroupItem {
        return ZEMTCheckGroupItem(
            text = name,
            icon = icon,
            id = conversationId
        )
    }
}
