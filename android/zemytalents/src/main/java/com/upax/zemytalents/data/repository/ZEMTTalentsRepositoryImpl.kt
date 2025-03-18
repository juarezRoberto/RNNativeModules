package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.local.database.dao.ZEMTTalentsCompletedDao
import com.upax.zemytalents.data.remote.api.ZEMTMyTalentsApi
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.data.remote.mapper.toModel
import com.upax.zemytalents.data.remote.mapper.toRoomEntity
import com.upax.zemytalents.data.remote.mapper.toTalentsUiModel
import com.upax.zemytalents.data.remote.requests.ZEMTTalentsCompletedByListRequest
import com.upax.zemytalents.domain.models.ZEMTCollaboratorTalentStatus
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.models.ZEMTTalents
import kotlinx.coroutines.delay

internal class ZEMTTalentsRepositoryImpl(
    private val myTalentsApi: ZEMTMyTalentsApi,
    private val talentsCompletedDao: ZEMTTalentsCompletedDao
) : ZEMTTalentsRepository {

    override suspend fun getTalents(
        collaboratorId: String
    ): ZEMTResult<ZEMTTalents, ZEMTDataError> {
        val result = performNetworkCallV3(
            call = {
                myTalentsApi.getTalents(collaboratorId)
            },
            onSuccess = {
                it.data?.talents!!
            },
            onError = { error ->
                error.toDataError()
            }
        )
        return when (result) {
            is ZCSCResult.Error -> ZEMTResult.Error(result.error)
            is ZCSCResult.Success -> ZEMTResult.Success(result.data.toTalentsUiModel())
        }
    }

    override suspend fun getTalentsCompletedByList(collaboratorIdList: List<String>): ZEMTResult<List<ZEMTCollaboratorTalentStatus>, ZEMTDataError> {
        val result = performNetworkCallV3(
            call = {
                myTalentsApi.getTalentsCompletedByList(
                    ZEMTTalentsCompletedByListRequest(
                        collaboratorsIdList = collaboratorIdList
                    )
                )
            },
            onSuccess = { response ->
                val talentsCompletedList =
                    response.data?.progressStages?.map { it.toRoomEntity() }.orEmpty()
                talentsCompletedDao.insertTalentsCompleted(talentsCompletedList)
                talentsCompletedDao.getTalentsCompletedList(collaboratorIdList).map { it.toModel() }
            },
            onError = { error ->
                error.toDataError()
            }
        )

        return when (result) {
            is ZCSCResult.Error -> ZEMTResult.Error(result.error)
            is ZCSCResult.Success -> ZEMTResult.Success(result.data)
        }
    }

}