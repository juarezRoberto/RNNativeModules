package com.upax.zemytalents.ui.modules.confirm.survey

import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmTalentUiModel

internal data class ZEMTSurveyConfirmUiState(
    val talents: List<ZEMTSurveyConfirmTalentUiModel> = listOf(),
    val openQuestionId: Int? = null,
    val showFinishedSurveyDialog: Boolean = false,
    val showTalentIntroduction: Boolean = true,
    val showRemainingQuestionsDialog: RemainingTalents? = null,
    val showSavedProgressDialog: Boolean = false
) {

    data class RemainingTalents(
        val remaining: Int
    )

    val currentTalent = talents.find { it.selected }

}