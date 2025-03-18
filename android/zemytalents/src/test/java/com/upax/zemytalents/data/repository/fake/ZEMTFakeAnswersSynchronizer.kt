package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer

internal class ZEMTFakeAnswersSynchronizer : ZEMTAnswersSynchronizer {

    var error: ZEMTDataError? = null
    var answersSynchronized: List<ZEMTAnswer> = emptyList()

    override suspend fun sync(
        collaboratorId: String,
        surveyId: ZEMTSurveyId,
        answers: List<ZEMTAnswer>
    ): ZEMTResult<Unit, ZEMTDataError> {
        error?.let {
            return ZEMTResult.Error(it)
        }
        answersSynchronized = answers
        return ZEMTResult.Success(Unit)
    }

}