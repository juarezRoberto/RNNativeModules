package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyTalentsRepository

internal class ZEMTGetSurveyConfirmUseCase(
    private val talentsRepository: ZEMTSurveyTalentsRepository,
    private val surveyConfirmRepository: ZEMTSurveyConfirmRepository
) {

    suspend operator fun invoke(): ZEMTResult<List<ZEMTSurveyTalent>, ZEMTDataError> {
        return talentsRepository.get(surveyConfirmRepository.getId())
    }
}