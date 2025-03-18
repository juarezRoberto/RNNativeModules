package com.upax.zemytalents.ui.conversations.talentsresume.model

enum class ZEMTConversationsErrorType(val key: String) {
    ERROR_RETRIEVING_TALENTS(ERROR_RETRIEVING_TALENTS_KEY),
    ERROR_RETRIEVING_CONVERSATIONS(ERROR_RETRIEVING_CONVERSATIONS_KEY),
    ERROR_RETRIEVING_TALENTS_FINISHED(ERROR_RETRIEVING_TALENTS_FINISHED_KEY),
    ERROR_RETRIEVING_CONVERSATIONS_HISTORY(ERROR_RETRIEVING_CONVERSATIONS_HISTORY_KEY),
    ERROR_RETRIEVING_SERVICE_TEXT(ERROR_RETRIEVING_SERVICE_TEXT_KEY),
    ERROR_RETRIEVING_COLLABORATORS_IN_CHARGE(ERROR_RETRIEVING_COLLABORATORS_IN_CHARGE_KEY),
    ERROR_RETRIEVING_TALENTS_COMPLETED(ERROR_RETRIEVING_TALENTS_COMPLETED_KEY),
}

private const val ERROR_RETRIEVING_TALENTS_KEY = "Error retrieving talents"
private const val ERROR_RETRIEVING_CONVERSATIONS_KEY = "Error retrieving conversations"
private const val ERROR_RETRIEVING_TALENTS_FINISHED_KEY = "Error retrieving talents finished"
private const val ERROR_RETRIEVING_CONVERSATIONS_HISTORY_KEY = "Error retrieving conversations history"
private const val ERROR_RETRIEVING_SERVICE_TEXT_KEY = "Error retrieving service text"
private const val ERROR_RETRIEVING_COLLABORATORS_IN_CHARGE_KEY = "Error retrieving collaborators in charge"
private const val ERROR_RETRIEVING_TALENTS_COMPLETED_KEY = "Error retrieving talents completed"
