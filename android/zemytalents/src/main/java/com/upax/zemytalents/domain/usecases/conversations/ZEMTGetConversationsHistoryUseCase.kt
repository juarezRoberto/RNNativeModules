package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationHistory
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetConversationsHistoryUseCase(private val repo: ZEMTConversationsRepository) {
    operator fun invoke(
        collaboratorId: String,
        bossId: String,
        conversationId: String
    ): Flow<ZEMTConversationResult<List<ZEMTConversationHistory>>> = flow {
        val keyMap = collaboratorId + bossId + conversationId
        val inMemoryList = isDataInMemory[keyMap]

        if (inMemoryList != null) emit(
            ZEMTConversationResult.Success(
                inMemoryList.filterConversation(
                    conversationId
                )
            )
        )
        else {
            emit(ZEMTConversationResult.Loading)
            when (val result = repo.fetchConversationsHistory(collaboratorId, bossId, conversationId)) {
                is ZEMTConversationResult.Success -> {
                    isDataInMemory[keyMap] = result.data
                    emit(
                        ZEMTConversationResult.Success(
                            result.data.filterConversation(
                                conversationId
                            )
                        )
                    )
                }

                is ZEMTConversationResult.Error -> emit(ZEMTConversationResult.Error(Exception()))
                else -> Unit
            }
        }
    }

    private fun List<ZEMTConversationHistory>.filterConversation(conversationId: String): List<ZEMTConversationHistory> {
        return this //TODO remove this
        return this.filter { it.phrase.conversation.conversationId == conversationId }
    }

    companion object {
        @JvmStatic
        private val isDataInMemory = mutableMapOf<String, List<ZEMTConversationHistory>>()

        fun reset() {
            isDataInMemory.clear()
        }
    }
}