package com.upax.zemytalents.ui.conversations.conversationhistory.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationHistory

internal data class ZEMTConversationHistoryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val conversations: List<ZEMTConversationHistory> = emptyList(),
    val showTipsAlert: Boolean = false,
    val tipAlertText: String = String.EMPTY
)