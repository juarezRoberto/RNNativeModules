package com.upax.zemytalents.ui.modules.confirm.survey.models

internal sealed interface ZEMTSurveyConfirmServiceState {
    data object Idle : ZEMTSurveyConfirmServiceState
    data object Loading : ZEMTSurveyConfirmServiceState
    data object Error : ZEMTSurveyConfirmServiceState
    data object Success : ZEMTSurveyConfirmServiceState
}