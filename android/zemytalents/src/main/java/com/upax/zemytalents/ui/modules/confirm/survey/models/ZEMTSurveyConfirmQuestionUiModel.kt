package com.upax.zemytalents.ui.modules.confirm.survey.models

internal data class ZEMTSurveyConfirmQuestionUiModel(
    val id: Int,
    val order: Int,
    val header: String,
    val text: String,
    val answerOptions: List<ZEMTSurveyConfirmAnswerOptionUiModel>,
    val isCompleted: Boolean
) {
    val currentAnswer = answerOptions.firstOrNull { !it.isChecked }
}