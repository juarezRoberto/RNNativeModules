package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetCollaboratorsInChargeUseCase(
    private val repo: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository
) {
    operator fun invoke(): Flow<ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>> =
        flow {
            if (isDataInMemory) emit(
                repo.getCollaboratorsInCharge(collaboratorId = userRepository.collaboratorId)
            )
            else {
                emit(ZEMTConversationResult.Loading)

                val result = repo.fetchCollaboratorsInCharge(
                    collaboratorId = userRepository.collaboratorId,
                    companyId = userRepository.getCompanyId()
                )
                if (result is ZEMTConversationResult.Success) isDataInMemory = true
                emit(result)
            }
        }

    companion object {
        @JvmStatic
        private var isDataInMemory = false

        fun reset(){
            isDataInMemory = false
        }
    }
}