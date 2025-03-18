package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ZEMTGetUserTalentsUseCase(private val repository: ZEMTTalentsRepository) {
    operator fun invoke(collaboratorId: String): Flow<ZEMTConversationResult<ZEMTTalents>> = flow {
        if (talentsInMemory[collaboratorId] != null) emit(
            ZEMTConversationResult.Success(
                talentsInMemory[collaboratorId]!!
            )
        )
        else {
            emit(ZEMTConversationResult.Loading)
            val result = repository.getTalents(collaboratorId)
            if (result is ZEMTResult.Success) talentsInMemory[collaboratorId] = result.data
            emit(
                when (result) {
                    is ZEMTResult.Error -> ZEMTConversationResult.Error(Exception())
                    is ZEMTResult.Success -> ZEMTConversationResult.Success(result.data)
                }
            )
        }
    }


    companion object {
        private var talentsInMemory = mutableMapOf<String, ZEMTTalents>()
        fun reset() {
            talentsInMemory = mutableMapOf()
        }
    }
}