package com.upax.zemytalents.domain.models.modules

internal data class ZEMTSurveyTalentQuestion(
    val id: Int,
    val order: Int,
    val header: String,
    val text: String,
    val answerOptions: List<ZEMTSurveyTalentQuestionOption>
) {

    val completed = answerOptions.all { it.isChecked }

}