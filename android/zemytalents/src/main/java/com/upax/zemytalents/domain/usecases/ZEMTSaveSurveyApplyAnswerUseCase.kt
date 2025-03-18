package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerSaved
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository

internal class ZEMTSaveSurveyApplyAnswerUseCase(
    private val surveyAnswersRepository: ZEMTSurveyApplyAnswersRepository,
    private val locationRepository: ZEMTLocationRepository,
    private val dateRepository: ZEMTDateRepository
) {

    suspend operator fun invoke(
        answerId: Int,
        questionId: Int,
        status: ZEMTSurveyApplyAnswerStatus
    ) {
        val answerToSave = ZEMTSurveyApplyAnswerSaved(
            id = answerId,
            questionId = questionId,
            status = status,
            location = locationRepository.getLocation(),
            date = dateRepository.currentDate(),
            order = 0
        )
        surveyAnswersRepository.saveAnswer(answerToSave)
    }

}