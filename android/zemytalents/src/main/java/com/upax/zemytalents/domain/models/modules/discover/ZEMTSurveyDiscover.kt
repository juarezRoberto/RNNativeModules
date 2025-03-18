package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.ZEMTSurveyId

internal data class ZEMTSurveyDiscover(
    val id: ZEMTSurveyId,
    val breaks: List<ZEMTBreakDiscover>,
    val groupQuestions: List<ZEMTGroupQuestionsDiscover>
)