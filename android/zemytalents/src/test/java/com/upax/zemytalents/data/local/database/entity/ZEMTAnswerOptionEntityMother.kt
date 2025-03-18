package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAnswerOptionEntityMother {

    fun apply(
        answerOptionId: Int = ZEMTRandomValuesUtil.getInt(),
        questionId: Int = ZEMTRandomValuesUtil.getInt(),
        position: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
    ): ZEMTAnswerOptionDiscoverEntity {
        return ZEMTAnswerOptionDiscoverEntity(
            answerOptionId = answerOptionId,
            questionId = questionId,
            order = position,
            text = text,
            value = value
        )
    }

    fun random() = apply()

}