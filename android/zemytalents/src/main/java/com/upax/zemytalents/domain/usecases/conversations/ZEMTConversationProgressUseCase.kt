package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress

internal class ZEMTConversationProgressUseCase(
    private val repo: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository
) {

    suspend operator fun invoke(
        conversationOwnerId: String = userRepository.collaboratorId,
        collaboratorId: String
    ) =
        repo.getProgressNotObservable(conversationOwnerId, collaboratorId)

    suspend fun updateProgress(progress: ZEMTMakeConversationProgress) =
        repo.updateProgress(progress.copy(conversationOwnerId = userRepository.collaboratorId))

    suspend fun deleteProgress(
        conversationOwnerId: String = userRepository.collaboratorId,
        collaboratorId: String
    ) = repo.deleteProgressById(
        conversationOwnerId = conversationOwnerId,
        collaboratorId = collaboratorId
    )
}