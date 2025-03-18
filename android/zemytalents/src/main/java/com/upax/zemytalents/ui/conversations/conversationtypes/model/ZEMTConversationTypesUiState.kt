package com.upax.zemytalents.ui.conversations.conversationtypes.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation

internal data class ZEMTConversationTypesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val conversationList: List<ZEMTConversation> = emptyList(),
    val tipMessage: String = String.EMPTY
)
