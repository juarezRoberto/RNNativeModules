package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover

internal class ZEMTAnswerDiscoverToEntityMapper :
    Function1<ZEMTAnswerOptionDiscover, ZEMTAnswerOptionDiscoverEntity> {

    override operator fun invoke(answer: ZEMTAnswerOptionDiscover): ZEMTAnswerOptionDiscoverEntity {
        return ZEMTAnswerOptionDiscoverEntity(
            answerOptionId = answer.id.value,
            questionId = answer.questionId.value,
            order = answer.order,
            text = answer.text,
            value = answer.value
        )
    }

}