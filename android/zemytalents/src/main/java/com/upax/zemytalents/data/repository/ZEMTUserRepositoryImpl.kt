package com.upax.zemytalents.data.repository

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zcsessioninfo.domain.repository.ZCSISessionInfo
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTUserRepositoryImpl(
    private val sessionInfo: ZCSISessionInfo
) : ZEMTUserRepository {

    override val collectUser
        get() = sessionInfo.observeUser()

    override val collaboratorId
        get() = sessionInfo.getZeusId()

    override val employeeNumber: String
        get() = sessionInfo.getEmployeeNumber()

    override suspend fun getLeaderId(): String {
        return sessionInfo.getLeader().zeusId
    }

    override suspend fun getUser(): ZCSIUser {
        return sessionInfo.getUser()
    }

    override suspend fun getFullName(): String {
        val user = sessionInfo.getUser()
        return "${user.name} ${user.lastName} ${user.secondLastName}"
    }

    override suspend fun getCompanyId(): String {
        return sessionInfo.getCompany().companyId
    }
}