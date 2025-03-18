package com.upax.zemytalents.domain.models.modules

internal data class ZEMTSurveyTalent(
    val id: Int,
    val name: String,
    val order: Int,
    val description: String,
    val questions: List<ZEMTSurveyTalentQuestion>,
) {

    val finished = questions.all { question -> question.completed }

}