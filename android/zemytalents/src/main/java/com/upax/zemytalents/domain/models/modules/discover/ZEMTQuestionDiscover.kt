package com.upax.zemytalents.domain.models.modules.discover

internal data class ZEMTQuestionDiscover(
    val id: ZEMTQuestionId,
    val text: String,
    val order: Int,
    val answers: List<ZEMTAnswerOptionDiscover>
)
