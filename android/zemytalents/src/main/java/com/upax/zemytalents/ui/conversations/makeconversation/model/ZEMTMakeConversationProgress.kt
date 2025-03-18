package com.upax.zemytalents.ui.conversations.makeconversation.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.common.ZEMTQrFunctions.parseDateForService
import java.time.LocalDateTime

internal data class ZEMTMakeConversationProgress(
    val conversationOwnerId: String = String.EMPTY,
    val collaboratorId: String = String.EMPTY,
    val comment: String = String.EMPTY,
    val isConversationMade: Boolean? = null,
    val phraseId: String = String.EMPTY,
    val conversationId: String = String.EMPTY,
    val currentStep: ZEMTMakeConversationStep = ZEMTMakeConversationStep.CHOOSE_CONVERSATION,
    val startDate: String = LocalDateTime.now().parseDateForService()
)
