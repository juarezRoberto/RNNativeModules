package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.remote.api.ZEMTMyTalentsApi
import com.upax.zemytalents.data.remote.mapper.ZEMTSurveyTalentsResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.repositories.ZEMTSurveyTalentsRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTRetrofitSurveyTalentsRepository(
    private val myTalentsApi: ZEMTMyTalentsApi,
    private val userRepository: ZEMTUserRepository,
    private val surveyTalentsResponseMapper: ZEMTSurveyTalentsResponseToModelMapper
): ZEMTSurveyTalentsRepository {

    override suspend fun get(
        surveyId: ZEMTSurveyId
    ): ZEMTResult<List<ZEMTSurveyTalent>, ZEMTDataError> {
        val result = performNetworkCallV3(
            call = {
                myTalentsApi.getSurveyTalent(surveyId.value, userRepository.collaboratorId)
            },
            onSuccess = {
                surveyTalentsResponseMapper.invoke(it.data!!)
            },
            onError = {
                it.toDataError()
            }
        )
        return when (result) {
            is ZCSCResult.Error -> ZEMTResult.Error(result.error)
            is ZCSCResult.Success -> ZEMTResult.Success(result.data)
        }
    }

}