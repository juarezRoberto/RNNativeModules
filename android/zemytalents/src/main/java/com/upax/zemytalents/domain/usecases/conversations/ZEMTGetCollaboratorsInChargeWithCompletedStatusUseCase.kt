package com.upax.zemytalents.domain.usecases.conversations

import com.upax.zemytalents.common.orFalse
import com.upax.zemytalents.data.local.database.conversations.dao.ZEMTCollaboratorInChargeDao
import com.upax.zemytalents.data.remote.mapper.ZEMTConversationsMapper.toRoomEntity
import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase(
    private val repo: ZEMTConversationsRepository,
    private val userRepository: ZEMTUserRepository,
    private val getTalentsCompletedByIdList: ZEMTGetTalentsCompletedByIdListUseCase,
    private val collaboratorInChargeDao: ZEMTCollaboratorInChargeDao
) {
    operator fun invoke(): Flow<ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>> =
        flow {
            if (isDataInMemory && ZEMTGetTalentsCompletedByIdListUseCase.isDataInMemory) emit(
                repo.getCollaboratorsInCharge(collaboratorId = userRepository.collaboratorId)
            )
            else {
                emit(ZEMTConversationResult.Loading)

                val collaboratorsInCharge =
                    if (isDataInMemory) repo.getCollaboratorsInCharge(collaboratorId = userRepository.collaboratorId)
                    else repo.fetchCollaboratorsInCharge(
                        collaboratorId = userRepository.collaboratorId,
                        companyId = userRepository.getCompanyId()
                    )
                when (collaboratorsInCharge) {
                    is ZEMTConversationResult.Success -> {
                        isDataInMemory = true
                        getTalentsCompletedByIdList
                            .invoke(collaboratorsInCharge.data.map { it.collaboratorId })
                            .collect { talentsCompletedResult ->
                                when (talentsCompletedResult) {
                                    is ZEMTConversationResult.Success -> {
                                        val finalCollaboratorsInChargeList =
                                            collaboratorsInCharge.data.map { collaborator ->
                                                collaborator.copy(
                                                    talentsCompleted = talentsCompletedResult.data.firstOrNull { collaborator.collaboratorId == it.collaboratorId }?.completed.orFalse()
                                                )
                                            }
                                        collaboratorInChargeDao.insertAll(
                                            finalCollaboratorsInChargeList.map {
                                                it.toRoomEntity(userRepository.collaboratorId)
                                            })
                                        emit(
                                            ZEMTConversationResult.Success(
                                                finalCollaboratorsInChargeList
                                            )
                                        )
                                    }

                                    is ZEMTConversationResult.Error -> emit(talentsCompletedResult)
                                    else -> Unit
                                }

                            }
                    }

                    is ZEMTConversationResult.Error -> emit(collaboratorsInCharge)
                    else -> Unit
                }
            }
        }

    companion object {
        @JvmStatic
        private var isDataInMemory = false

        fun reset() {
            isDataInMemory = false
        }
    }
}