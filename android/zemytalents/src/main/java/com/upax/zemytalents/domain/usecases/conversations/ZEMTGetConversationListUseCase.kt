package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetConversationListUseCase(
    private val conversationRepository: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository
) {
    operator fun invoke(collaboratorId: String = userRepository.collaboratorId): Flow<ZEMTConversationResult<List<ZEMTConversation>>> =
        flow {
            if (isConversationListInMemory) emit(conversationRepository.getConversations())
            else {
                emit(ZEMTConversationResult.Loading)
                val result = conversationRepository.fetchConversations(
                    collaboratorId = collaboratorId,
                )
                if (result is ZEMTConversationResult.Success) isConversationListInMemory = true
                emit(result)
            }
        }

    companion object {
        @JvmStatic
        private var isConversationListInMemory = false

        fun reset() {
            isConversationListInMemory = false
        }
    }
}