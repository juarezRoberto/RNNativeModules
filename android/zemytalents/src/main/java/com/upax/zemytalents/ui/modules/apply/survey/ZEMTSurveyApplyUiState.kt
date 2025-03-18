package com.upax.zemytalents.ui.modules.apply.survey

import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyTalentUiModel

internal data class ZEMTSurveyApplyUiState(
    val dominantTalents: List<ZEMTTalent> = emptyList(),
    val surveyTalents: List<ZEMTSurveyApplyTalentUiModel> = listOf()
) {

    val currentTalent = surveyTalents.find { it.selected }
    val totalTalentsPendingToAnswer = surveyTalents.count { !it.finished } - 1
    val currentQuestion = currentTalent?.question
    val nextTalent = surveyTalents.firstOrNull { !it.finished }
    val surveyFinished = nextTalent == null && surveyTalents.isNotEmpty()
    val isInProgress = surveyTalents.any { it.atLeastOneAnswerChecked }

    fun updateTalent(talent: ZEMTSurveyApplyTalentUiModel): ZEMTSurveyApplyUiState {
        return this.copy(surveyTalents = surveyTalents.map {
            if (it.id == talent.id) talent else it
        })
    }

}