package com.upax.zemytalents.domain.usecases.modules.confirm

import com.upax.zemytalents.data.local.database.entity.confirm.ZEMTSurveyConfirmAnswerSavedEntity
import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmQuestionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmTalentUiModel

internal class ZEMTSaveConfirmSurveyAnswerUseCase(
    private val roomSurveyConfirmRepository: ZEMTSurveyConfirmRepository,
    private val locationRepository: ZEMTLocationRepository,
    private val dateRepository: ZEMTDateRepository,
) {

    suspend operator fun invoke(
        currentTalent: ZEMTSurveyConfirmTalentUiModel,
        currentQuestion: ZEMTSurveyConfirmQuestionUiModel,
        currentAnswer: ZEMTSurveyConfirmAnswerOptionUiModel
    ) {
        val currentLocation = locationRepository.getLocation()
        val currentDate = dateRepository.currentDate()

        val entity = ZEMTSurveyConfirmAnswerSavedEntity(
            answerId = currentAnswer.id,
            answerOrder = currentAnswer.order,
            questionId = currentQuestion.id,
            questionOrder = currentQuestion.order,
            talentId = currentTalent.id,
            talentOrder = currentTalent.order,
            date = currentDate,
            latitude = currentLocation.latitude,
            longitude = currentLocation.longitude
        )
        roomSurveyConfirmRepository.saveConfirmSurveyAnswer(entity)
    }
}