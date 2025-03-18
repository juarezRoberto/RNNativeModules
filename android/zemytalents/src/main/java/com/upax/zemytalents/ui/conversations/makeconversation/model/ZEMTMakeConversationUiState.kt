package com.upax.zemytalents.ui.conversations.makeconversation.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

internal data class ZEMTMakeConversationUiState(
    val conversationData: ZEMTMakeConversationProgress = ZEMTMakeConversationProgress(),
    val conversationList: List<ZEMTCheckGroupItem> = emptyList(),
    val phraseList: List<ZEMTCheckGroupItem> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSaveConversationError: Boolean = false,
    val isSaveConversationSuccess: Boolean = false,
    val captions: ZEMTMakeConversationCaptions = ZEMTMakeConversationCaptions(),
)

internal data class ZEMTMakeConversationCaptions(
    val step2Caption: String = String.EMPTY,
    val step4Caption: String = String.EMPTY,
)
