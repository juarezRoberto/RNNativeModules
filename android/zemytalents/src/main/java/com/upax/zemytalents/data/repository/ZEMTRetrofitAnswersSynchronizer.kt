package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.remote.api.ZEMTSurveyDiscoverApiService
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.data.remote.requests.ZEMTSyncSurveyAnswersRequestBuilder
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer

internal class ZEMTRetrofitAnswersSynchronizer(
    private val api: ZEMTSurveyDiscoverApiService,
    private val requestBuilder: ZEMTSyncSurveyAnswersRequestBuilder
) : ZEMTAnswersSynchronizer {

    override suspend fun sync(
        collaboratorId: String,
        surveyId: ZEMTSurveyId,
        answers: List<ZEMTAnswer>
    ): ZEMTResult<Unit, ZEMTDataError> {

        val request = requestBuilder.build(collaboratorId, answers)

        val result = performNetworkCallV3(
            call = {
                api.syncSurvey(surveyId = surveyId.value, request = request)
            },
            onSuccess = { _ -> },
            onError = { error -> error.toDataError() }
        )

        return when (result) {
            is ZCSCResult.Error -> ZEMTResult.Error(result.error)
            is ZCSCResult.Success -> ZEMTResult.Success(result.data)
        }
    }

}