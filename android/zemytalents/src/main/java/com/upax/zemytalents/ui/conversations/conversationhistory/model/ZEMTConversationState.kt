package com.upax.zemytalents.ui.conversations.conversationhistory.model

sealed class ZEMTConversationState(open val date: String) {
    data class Uncompleted(override val date: String, val title: String, val message: String) :
        ZEMTConversationState(date)

    data class Completed(override val date: String, val comment: String, val phrase: String) : ZEMTConversationState(date)
}