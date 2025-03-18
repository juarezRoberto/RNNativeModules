package com.upax.zemytalents.data.remote.responses.conversations

import com.google.gson.annotations.SerializedName

internal data class ZEMTSaveConversationResponse(
    @SerializedName("conversation_history_id") val conversationHistoryId: String? = null,
)

internal data class ZEMTSaveConversationResponseWrapper(
    @SerializedName("conversation") val conversation: ZEMTSaveConversationResponse? = null
)
