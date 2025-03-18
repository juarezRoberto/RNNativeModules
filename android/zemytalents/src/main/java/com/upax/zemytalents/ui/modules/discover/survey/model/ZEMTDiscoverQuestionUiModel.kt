package com.upax.zemytalents.ui.modules.discover.survey.model

import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal data class ZEMTDiscoverQuestionUiModel(
    val id: ZEMTQuestionId,
    val text: String
)