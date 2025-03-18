package com.upax.zemytalents.domain.repositories

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import kotlinx.coroutines.flow.Flow

internal interface ZEMTUserRepository {

    val collectUser: Flow<ZCSIUser>

    val collaboratorId: String

    val employeeNumber: String

    suspend fun getLeaderId(): String

    suspend fun getUser(): ZCSIUser

    suspend fun getFullName(): String

    suspend fun getCompanyId(): String
}