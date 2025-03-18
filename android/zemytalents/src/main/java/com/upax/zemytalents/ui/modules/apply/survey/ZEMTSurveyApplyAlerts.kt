package com.upax.zemytalents.ui.modules.apply.survey

sealed interface ZEMTSurveyApplyAlerts {
    data class TalentApplied(
        val talent: String,
        val remainingTalents: Int
    ) : ZEMTSurveyApplyAlerts

    data object SurveyFinished : ZEMTSurveyApplyAlerts
    data object SurveyDownloadedError : ZEMTSurveyApplyAlerts
    data object SurveyAnswerSyncError : ZEMTSurveyApplyAlerts
    data object SurveyInProgress : ZEMTSurveyApplyAlerts
}