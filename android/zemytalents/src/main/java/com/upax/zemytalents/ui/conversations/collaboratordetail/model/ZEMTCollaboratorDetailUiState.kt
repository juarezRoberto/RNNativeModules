package com.upax.zemytalents.ui.conversations.collaboratordetail.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation

internal data class ZEMTCollaboratorDetailUiState(
    val conversationList: List<ZEMTConversation> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val leaderId: String = String.EMPTY
)
