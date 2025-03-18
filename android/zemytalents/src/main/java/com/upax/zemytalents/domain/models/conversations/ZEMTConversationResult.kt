package com.upax.zemytalents.domain.models.conversations

internal sealed interface ZEMTConversationResult<out T> {
    data class Error(val error: Exception) : ZEMTConversationResult<Nothing>
    data class Success<out T>(val data: T) : ZEMTConversationResult<T>
    data object Loading : ZEMTConversationResult<Nothing>
    data object Empty : ZEMTConversationResult<Nothing>
}