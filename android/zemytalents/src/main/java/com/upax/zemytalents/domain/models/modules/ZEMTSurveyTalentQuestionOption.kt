package com.upax.zemytalents.domain.models.modules

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus

internal data class ZEMTSurveyTalentQuestionOption(
    val id: Int,
    val order: Int,
    val text: String,
    val value: Int,
    val status: ZEMTSurveyApplyAnswerStatus
) {

    val isChecked = status != ZEMTSurveyApplyAnswerStatus.UNCHECKED

}