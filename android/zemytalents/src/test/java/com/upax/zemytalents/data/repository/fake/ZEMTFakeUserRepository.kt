package com.upax.zemytalents.data.repository.fake

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import kotlinx.coroutines.flow.Flow

internal class ZEMTFakeUserRepository(
    private val fullName: String = ZEMTRandomValuesUtil.getString(),
    private val companyId: String = ZEMTRandomValuesUtil.getString(),
    private val _collaboratorId: String = ZEMTRandomValuesUtil.getString(),
    private val _employeeNumber: String = ZEMTRandomValuesUtil.getString(),
    private val leaderId: String = ZEMTRandomValuesUtil.getString()
) : ZEMTUserRepository {

    override val collectUser: Flow<ZCSIUser>
        get() = throw Exception()

    override val collaboratorId: String
        get() = _collaboratorId

    override val employeeNumber: String
        get() = _employeeNumber

    override suspend fun getLeaderId(): String = leaderId

    override suspend fun getUser(): ZCSIUser {
        throw Exception()
    }

    override suspend fun getFullName(): String = fullName

    override suspend fun getCompanyId(): String = companyId

}