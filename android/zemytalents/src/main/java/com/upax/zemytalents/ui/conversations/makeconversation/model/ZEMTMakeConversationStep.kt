package com.upax.zemytalents.ui.conversations.makeconversation.model

internal enum class ZEMTMakeConversationStep {
    CHOOSE_CONVERSATION, CHOOSE_PHRASE, SHOW_PHRASE, SUMMARY;

    fun stepToIndex(): Int = this.ordinal
    fun hasNext() = this != SUMMARY
    fun getNextStep() = when (this) {
        CHOOSE_CONVERSATION -> CHOOSE_PHRASE
        CHOOSE_PHRASE -> SHOW_PHRASE
        SHOW_PHRASE -> SUMMARY
        SUMMARY -> SUMMARY
    }

    fun getPrevStep() = when (this) {
        CHOOSE_CONVERSATION -> CHOOSE_CONVERSATION
        CHOOSE_PHRASE -> CHOOSE_CONVERSATION
        SHOW_PHRASE -> CHOOSE_PHRASE
        SUMMARY -> SHOW_PHRASE
    }

    fun isChooseConversation() = this == CHOOSE_CONVERSATION
    fun isChoosePhrase() = this == CHOOSE_PHRASE
    fun isShowPhrase() = this == SHOW_PHRASE
    fun isSummary() = this == SUMMARY

    companion object {
        fun indexToStep(index: Int): ZEMTMakeConversationStep = entries[index]
    }
}