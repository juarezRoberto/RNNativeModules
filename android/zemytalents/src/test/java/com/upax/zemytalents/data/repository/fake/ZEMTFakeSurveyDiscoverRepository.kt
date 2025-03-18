package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal class ZEMTFakeSurveyDiscoverRepository(
    private val surveyId: ZEMTSurveyId = ZEMTSurveyId(ZEMTRandomValuesUtil.getInt().toString())
): ZEMTSurveyDiscoverRepository {

    override suspend fun getId(): ZEMTSurveyId = surveyId

}