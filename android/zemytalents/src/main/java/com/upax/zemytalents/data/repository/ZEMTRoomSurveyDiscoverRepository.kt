package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverRepository

internal class ZEMTRoomSurveyDiscoverRepository(
    private val surveyDao: ZEMTSurveyDiscoverDao
): ZEMTSurveyDiscoverRepository {

    override suspend fun getId(): ZEMTSurveyId {
        return ZEMTSurveyId(surveyDao.getSurvey()?.surveyId.toString())
    }

}