package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.data.remote.requests.ZEMTSaveConversationBodyRequest
import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTDeviceRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal data class ZEMTSaveConversationUseCase(
    private val repository: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository,
    private val deviceRepository: ZEMTDeviceRepository
) {
    operator fun invoke(makeConversationData: ZEMTMakeConversationProgress): Flow<ZEMTConversationResult<Unit>> =
        flow {
            emit(ZEMTConversationResult.Loading)
            val result = repository.saveConversation(
                conversationId = makeConversationData.conversationId,
                body = ZEMTSaveConversationBodyRequest(
                    bossId = userRepository.collaboratorId,
                    collaboratorId = makeConversationData.collaboratorId,
                    phraseId = makeConversationData.phraseId,
                    isRealized = makeConversationData.isConversationMade.orFalse(),
                    startDate = makeConversationData.startDate,
                    comment = makeConversationData.comment,
                    device = deviceRepository.name,
                    platform = deviceRepository.platform
                )
            )
            if (result is ZEMTConversationResult.Success) repository.deleteProgressById(
                conversationOwnerId = userRepository.collaboratorId,
                collaboratorId = makeConversationData.collaboratorId
            )
            emit(result)
        }
}
