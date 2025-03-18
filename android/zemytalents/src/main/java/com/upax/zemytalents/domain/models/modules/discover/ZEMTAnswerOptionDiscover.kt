package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId

internal data class ZEMTAnswerOptionDiscover(
    val id: ZEMTAnswerId,
    val questionId: ZEMTQuestionId,
    val order: Int,
    val text: String,
    val value: Int,
) {

    val isNeutralAnswer = value == 0

}
