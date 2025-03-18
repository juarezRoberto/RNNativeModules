package com.upax.zemytalents.domain.models.conversations

import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

internal data class ZEMTPhrase(
    val id: String,
    val phrase: String,
    val conversationId: String,
) {
    fun toCheckGroupItem(): ZEMTCheckGroupItem {
        return ZEMTCheckGroupItem(
            text = phrase,
            icon = ZEMTIconParser.NONE.iconId,
            id = id
        )
    }
}
