package com.upax.zemytalents.ui.modules.apply.survey.model

internal data class ZEMTSurveyApplyAnswerOptionUiModel(
    val id: Int,
    val questionId: Int,
    val order: Int,
    val text: String,
    val value: Int,
    val status: Status
) {

    val isChecked = status != Status.UNCHECKED

    enum class Status {
        UNCHECKED, CHECK_NEGATIVE, CHECK_POSITIVE
    }

}