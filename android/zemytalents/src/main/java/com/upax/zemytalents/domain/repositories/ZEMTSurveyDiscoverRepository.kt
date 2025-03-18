package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId

internal interface ZEMTSurveyDiscoverRepository {

    suspend fun getId(): ZEMTSurveyId

}