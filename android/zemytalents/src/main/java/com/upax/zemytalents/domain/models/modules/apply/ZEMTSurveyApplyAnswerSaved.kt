package com.upax.zemytalents.domain.models.modules.apply

import com.upax.zemytalents.domain.models.ZEMTLocation

internal data class ZEMTSurveyApplyAnswerSaved(
    val id: Int,
    val questionId: Int,
    val status: ZEMTSurveyApplyAnswerStatus,
    val location: ZEMTLocation,
    val date: String,
    val order: Int
)