package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal data class ZEMTGroupQuestionsDiscover(
    val index: ZEMTGroupQuestionIndexDiscover,
    val leftQuestion: ZEMTQuestionDiscover,
    val rightQuestion: ZEMTQuestionDiscover
)