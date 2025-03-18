package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTModuleDao
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyRepository

internal class ZEMTRoomSurveyApplyRepository(
    private val moduleDao: ZEMTModuleDao
) : ZEMTSurveyApplyRepository {

    override suspend fun getId(): ZEMTSurveyId {
        return ZEMTSurveyId(moduleDao.getModuleByStage(ZEMTModuleStage.APPLY.name)!!.surveyId)
    }

    override suspend fun finish() {
        moduleDao.updateCompleteStatus(getId().value.toInt(), true)
    }

}