package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.ZEMTAnswer
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId

internal class ZEMTAnswerSavedDiscover(
    id: ZEMTAnswerId,
    questionId: ZEMTQuestionId,
    val value: Int,
    val groupQuestionIndex: Int,
    date: String,
    location: ZEMTLocation
): ZEMTAnswer(id = id, questionId = questionId, date = date, location = location) {

    val isNeutralAnswer = value == 0

    override val position: Int
        get() = groupQuestionIndex

}