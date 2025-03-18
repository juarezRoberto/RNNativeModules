package com.upax.zemytalents.data.repository.test

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
class ZEMTStubUserRepository: ZEMTUserRepository {

    override val collectUser: Flow<ZCSIUser>
        get() = throw UnsupportedOperationException()

    override val collaboratorId: String
        get() = COLLABORATOR_ID

    override val employeeNumber: String
        get() = "10034091"

    override suspend fun getLeaderId(): String = "54321"

    override suspend fun getUser(): ZCSIUser {
        throw UnsupportedOperationException()
    }

    override suspend fun getFullName(): String = "Carlos"

    override suspend fun getCompanyId(): String = "321"

    companion object {
        const val COLLABORATOR_ID = "1234-5"
    }

}