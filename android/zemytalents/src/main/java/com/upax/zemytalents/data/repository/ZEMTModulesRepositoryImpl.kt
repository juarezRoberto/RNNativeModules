package com.upax.zemytalents.data.repository

import com.upax.zcservicecoordinator.expose.models.ZCSCResult
import com.upax.zcservicecoordinator.expose.utils.performNetworkCallV3
import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.data.local.database.dao.ZEMTModuleMultimediaDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import com.upax.zemytalents.data.local.database.mapper.toEntity
import com.upax.zemytalents.data.local.database.mapper.toEntityModels
import com.upax.zemytalents.data.local.database.mapper.toUiModel
import com.upax.zemytalents.data.local.database.mapper.toUiModels
import com.upax.zemytalents.data.remote.api.ZEMTMyTalentsApi
import com.upax.zemytalents.data.remote.mapper.toDataError
import com.upax.zemytalents.data.remote.mapper.toDomainModels
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTModule
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ZEMTModulesRepositoryImpl(
    private val myTalentsApi: ZEMTMyTalentsApi,
    private val userRepository: ZEMTUserRepository,
    private val moduleDao: ZEMTModuleDao,
    private val moduleMultimediaDao: ZEMTModuleMultimediaDao,
    private val surveyDiscoverDao: ZEMTSurveyDiscoverDao
) : ZEMTModulesRepository {

    override suspend fun getModules(): ZEMTResult<List<ZEMTModule>, ZEMTDataError> =
        withContext(Dispatchers.IO) {
            val result = performNetworkCallV3(
                call = {
                    myTalentsApi.getModules(userRepository.collaboratorId)
                },
                onSuccess = {
                    val domainModules = it.data!!.modules!!
                        .sortedBy { module -> module.order }
                        .toDomainModels()
                    saveModules(domainModules)

                    domainModules
                },
                onError = { error ->
                    error.toDataError()
                }
            )
            when (result) {
                is ZCSCResult.Error -> ZEMTResult.Error(result.error)
                is ZCSCResult.Success -> ZEMTResult.Success(result.data)
            }
        }

    private suspend fun saveModules(domainModules: List<ZEMTModule>) {
        domainModules.let { modules ->
            modules.forEach { module ->
                moduleDao.insert(module.toEntity())
                moduleMultimediaDao.insertAll(module.multimedia.toEntityModels(module.moduleId))
            }
            modules.find { it.stage == ZEMTModuleStage.DISCOVER }?.let {
                saveDiscoverSurvey(it.surveyId.toInt())
            }
        }
    }

    private suspend fun saveDiscoverSurvey(surveyId: Int) {
        surveyDiscoverDao.insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(
                surveyId = surveyId,
                groupQuestionIndex = 0
            )
        )
    }

    override fun collectModules(): Flow<List<ZEMTModuleUiModel>> {
        return moduleDao.collectModulesByOrder()
            .map { entities ->
                entities.map {
                    val multimediaEntityModels = moduleMultimediaDao
                        .getMultimediaByModuleId(it.moduleId)
                    it.toUiModel(multimediaEntityModels.toUiModels())
                }
            }
            .flowOn(Dispatchers.IO)
    }
}