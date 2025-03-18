package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId

internal interface ZEMTAnswersSynchronizer {

    suspend fun sync(
        collaboratorId: String,
        surveyId: ZEMTSurveyId,
        answers: List<ZEMTAnswer>
    ): ZEMTResult<Unit, ZEMTDataError>

}