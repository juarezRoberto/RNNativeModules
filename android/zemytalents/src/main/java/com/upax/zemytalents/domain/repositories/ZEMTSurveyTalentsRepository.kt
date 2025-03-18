package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent

internal interface ZEMTSurveyTalentsRepository {

    suspend fun get(
        surveyId: ZEMTSurveyId
    ): ZEMTResult<List<ZEMTSurveyTalent>, ZEMTDataError>

}