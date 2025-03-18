package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.data.remote.mapper.toModel
import com.upax.zemytalents.domain.models.ZEMTCollaboratorTalentStatus
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetTalentsCompletedByIdListUseCase(
    private val repo: ZEMTTalentsRepository,
    private val talentsCompletedDao: ZEMTTalentsCompletedDao
) {
    /**
     * Returns Success(true) if collaboratorId is in the list of collaborators completed
     * Returns Success(false) if collaboratorId is not in the list of collaborators completed
     * Returns Error if service fails
     * */
    operator fun invoke(collaboratorIdList: List<String>): Flow<ZEMTConversationResult<List<ZEMTCollaboratorTalentStatus>>> =
        flow {
            val localData = talentsCompletedDao.getTalentsCompletedList(collaboratorIdList)
            if (isDataInMemory && localData.isNotEmpty()) emit(
                ZEMTConversationResult.Success(
                    localData.map { it.toModel() })
            )
            else {
                emit(ZEMTConversationResult.Loading)
                emit(
                    when (val result = repo.getTalentsCompletedByList(collaboratorIdList)) {
                        is ZEMTResult.Success -> {
                            isDataInMemory = true
                            ZEMTConversationResult.Success(result.data)
                        }
                        is ZEMTResult.Error -> ZEMTConversationResult.Error(Exception())
                    }
                )
            }
        }

    companion object {
        var isDataInMemory = false
            private set

        fun reset() {
            isDataInMemory = false
        }
    }
}