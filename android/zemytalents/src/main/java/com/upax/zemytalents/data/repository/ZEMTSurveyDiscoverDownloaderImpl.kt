package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.data.remote.api.ZEMTSurveyDiscoverApiService
import com.upax.zemytalents.data.remote.mapper.ZEMTSurveyDiscoverResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.flatMap
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTSurveyDiscover
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverDownloader
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTSurveyDiscoverDownloaderImpl(
    private val api: ZEMTSurveyDiscoverApiService,
    private val discoverSurveyResponseToModelMapper: ZEMTSurveyDiscoverResponseToModelMapper,
    private val surveyRegistrar: ZEMTSurveyDiscoverRegistrar,
    private val surveyDao: ZEMTSurveyDiscoverDao,
    private val moduleDao: ZEMTModuleDao,
    private val userRepository: ZEMTUserRepository
) : ZEMTSurveyDiscoverDownloader {

    override suspend fun needToDownload() = !existSurveyInDatabase()

    private suspend fun existSurveyInDatabase(): Boolean {
        return surveyDao.getCurrentGroupQuestionIndex() > 0
    }

    override suspend fun download(): ZEMTResult<Unit, ZEMTDataError> {
        val module = moduleDao.getModuleByStage(ZEMTModuleStage.DISCOVER.name)
        return getSurveyFromRemote(module?.surveyId.orEmpty()).flatMap {
            surveyRegistrar.invoke(it)
            ZEMTResult.Success(Unit)
        }
    }

    private suspend fun getSurveyFromRemote(
        surveyId: String
    ): ZEMTResult<ZEMTSurveyDiscover, ZEMTDataError> {
        val result = performNetworkCallV3(
            call = {
                api.getSurvey(surveyId = surveyId, collaboratorId = userRepository.collaboratorId)
            },
            onSuccess = { response ->
                discoverSurveyResponseToModelMapper.invoke(
                    response.data!!,
                    ZEMTSurveyId(surveyId)
                )
            },
            onError = { error -> error.toDataError() }
        )

        return when (result) {
            is ZCSCResult.Error -> ZEMTResult.Error(result.error)
            is ZCSCResult.Success -> ZEMTResult.Success(result.data)
        }
    }

}