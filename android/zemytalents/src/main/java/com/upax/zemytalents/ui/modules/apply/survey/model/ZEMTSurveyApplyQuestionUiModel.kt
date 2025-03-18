package com.upax.zemytalents.ui.modules.apply.survey.model

internal data class ZEMTSurveyApplyQuestionUiModel(
    val id: Int,
    val order: Int,
    val header: String,
    val text: String,
    val answerOptions: List<ZEMTSurveyApplyAnswerOptionUiModel>,
    val isCompleted: Boolean
) {

    val currentAnswer = answerOptions.firstOrNull { !it.isChecked }

    val allAnswersChecked = answerOptions.all { it.isChecked }

    val atLeastOneAnswerChecked = answerOptions.any { it.isChecked }

    fun updateAnswer(
        answer: ZEMTSurveyApplyAnswerOptionUiModel
    ): ZEMTSurveyApplyQuestionUiModel {
        return this.copy(answerOptions = answerOptions.map {
            if (it.id == answer.id) answer else it
        })
    }

}