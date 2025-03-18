package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import kotlinx.coroutines.flow.flow

internal class ZEMTGetPhrasesUseCase(
    private val repo: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository
) {
    operator fun invoke(conversationId: String, collaboratorId: String) = flow {
        val mapKey = conversationId + collaboratorId
        if (phrasesMap[mapKey] == true) emit(repo.getPhrases(conversationId = conversationId, collaboratorId = collaboratorId)) else {
            emit(ZEMTConversationResult.Loading)
            val result = repo.fetchPhrases(
                conversationId = conversationId,
                collaboratorId = collaboratorId
            )
            if (result is ZEMTConversationResult.Success) phrasesMap[mapKey] = true
            emit(result)
        }
    }

    companion object {
        private val phrasesMap = mutableMapOf<String, Boolean>()
        fun reset() {
            phrasesMap.clear()
        }
    }
}