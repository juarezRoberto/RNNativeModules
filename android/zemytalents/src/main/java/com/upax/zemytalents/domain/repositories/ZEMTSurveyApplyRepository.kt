package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId

internal interface ZEMTSurveyApplyRepository {

    suspend fun getId(): ZEMTSurveyId

    suspend fun finish()

}