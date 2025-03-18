package com.upax.zemytalents.data.remote.responses.conversations

import com.google.gson.annotations.SerializedName

internal data class ZEMTConversationListResponse(@SerializedName("conversations") val conversations: List<ZEMTConversationResponse>? = null)

internal data class ZEMTConversationResponse(
    @SerializedName("conversation_id") val conversationId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("order") val order: Int? = null,
    @SerializedName("attachments") val attachments: List<ZEMTConversationAttachmentResponse>? = null,
)

internal data class ZEMTConversationAttachmentResponse(
    @SerializedName("url") val url: String? = null,
    @SerializedName("type") val type: Int? = null,
)