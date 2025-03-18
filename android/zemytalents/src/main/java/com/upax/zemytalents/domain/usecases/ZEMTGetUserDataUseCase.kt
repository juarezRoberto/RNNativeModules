package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTGetUserDataUseCase(
    private val repository: ZEMTUserRepository
) {
    operator fun invoke() = repository.collectUser

    suspend fun getUser() = repository.getUser();

    suspend fun getCollaboratorId() = repository.collaboratorId

    suspend fun getLeaderId() = repository.getLeaderId()
}