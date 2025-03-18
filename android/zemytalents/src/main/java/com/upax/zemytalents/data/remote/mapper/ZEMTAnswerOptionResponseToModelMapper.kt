package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTAnswerOptionDiscoverResponse
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal class ZEMTAnswerOptionResponseToModelMapper {

    operator fun invoke(
        answerOptions: List<ZEMTAnswerOptionDiscoverResponse?>?,
        questionId: ZEMTQuestionId
    ): List<ZEMTAnswerOptionDiscover> {
        return answerOptions?.mapNotNull {
            if (it == null) return@mapNotNull null
            ZEMTAnswerOptionDiscover(
                id = ZEMTAnswerId(it.id.orZero()),
                questionId = questionId,
                order = it.order.orZero(),
                text = it.text.orEmpty(),
                value = it.value.orEmpty().toInt()
            )
        } ?: emptyList()
    }

}