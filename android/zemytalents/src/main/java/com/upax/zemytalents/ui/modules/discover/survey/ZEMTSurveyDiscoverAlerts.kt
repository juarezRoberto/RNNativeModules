package com.upax.zemytalents.ui.modules.discover.survey

internal sealed interface ZEMTSurveyDiscoverAlerts {
    data object SurveyDownloadError: ZEMTSurveyDiscoverAlerts
    data object MaxNumberOfNeutralAnswers: ZEMTSurveyDiscoverAlerts
    data object SurveyAnswerSynchronizerError: ZEMTSurveyDiscoverAlerts
    data object SavePoint: ZEMTSurveyDiscoverAlerts
    data object FirstTime: ZEMTSurveyDiscoverAlerts
    data object TakeBreak: ZEMTSurveyDiscoverAlerts
}