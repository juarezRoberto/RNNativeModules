package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetTalentsCompletedByIdUseCase(
    private val repo: ZEMTTalentsRepository,
    private val userRepository: ZEMTUserRepository,
    private val talentsCompletedDao: ZEMTTalentsCompletedDao
) {
    /**
     * Returns Success(true) if collaboratorId is in the list of collaborators completed
     * Returns Success(false) if collaboratorId is not in the list of collaborators completed
     * Returns Error if service fails
     * */
    operator fun invoke(collaboratorId: String = userRepository.collaboratorId): Flow<ZEMTConversationResult<Boolean>> =
        flow {
            val localData = talentsCompletedDao.getTalentsCompletedById(collaboratorId)
            if (isDataInMemory[collaboratorId].orFalse() && localData != null) emit(
                ZEMTConversationResult.Success(localData.completed)
            )
            else {
                emit(ZEMTConversationResult.Loading)
                emit(
                    when (val result = repo.getTalentsCompletedByList(listOf(collaboratorId))) {
                        is ZEMTResult.Success -> {
                            isDataInMemory[collaboratorId] = true
                            ZEMTConversationResult.Success(result.data.firstOrNull { it.collaboratorId == collaboratorId }?.completed.orFalse())
                        }

                        is ZEMTResult.Error -> ZEMTConversationResult.Error(Exception())
                    }
                )
            }
        }

    companion object {
        private val isDataInMemory = mutableMapOf<String, Boolean>()

        fun reset() {
            isDataInMemory.clear()
        }
    }
}