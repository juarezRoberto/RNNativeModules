package com.upax.zemytalents.ui.modules.confirm.survey.models

internal data class ZEMTSurveyConfirmAnswerOptionUiModel(
    val id: Int,
    val order: Int,
    val text: String,
    val value: Int,
    val isChecked: Boolean
)