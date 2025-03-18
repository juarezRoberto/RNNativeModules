package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.data.remote.mapper.toModel
import com.upax.zemytalents.domain.models.ZEMTCollaboratorTalentStatus
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.onSuccess
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

internal class ZEMTInMemoryTalentsRepository private constructor(
    private val talentsRepository: ZEMTTalentsRepository,
    private val talentsCompletedDao: ZEMTTalentsCompletedDao
) : ZEMTTalentsRepository {

    private val talentsCache = ConcurrentHashMap<String, ZEMTTalents>()

    override suspend fun getTalents(
        collaboratorId: String
    ): ZEMTResult<ZEMTTalents, ZEMTDataError> {
        talentsCache[collaboratorId]?.let {
            return ZEMTResult.Success(it)
        }

        return talentsRepository.getTalents(collaboratorId).onSuccess {
            talentsCache[collaboratorId] = it
        }
    }

    override suspend fun getTalentsCompletedByList(collaboratorIdList: List<String>): ZEMTResult<List<ZEMTCollaboratorTalentStatus>, ZEMTDataError> = withContext(Dispatchers.IO) {
        val result = talentsCompletedDao.getTalentsCompletedList(collaboratorIdList).map { it.toModel() }
        return@withContext ZEMTResult.Success(result)
    }


    companion object {
        @Volatile
        private var INSTANCE: ZEMTInMemoryTalentsRepository? = null

        fun getInstance(talentsRepository: ZEMTTalentsRepository, talentsCompletedDao: ZEMTTalentsCompletedDao): ZEMTInMemoryTalentsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ZEMTInMemoryTalentsRepository(talentsRepository, talentsCompletedDao).also {
                    INSTANCE = it
                }
            }
        }

        fun destroyInstance() {
            synchronized(this) {
                INSTANCE = null
            }
        }

    }


}