package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAnswerOptionDiscoverMother {

    fun apply(
        id: ZEMTAnswerId = ZEMTAnswerId(ZEMTRandomValuesUtil.getInt()),
        questionId: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
    ): ZEMTAnswerOptionDiscover {
        return ZEMTAnswerOptionDiscover(
            id = id,
            questionId = questionId,
            order = order,
            text = text,
            value = value
        )
    }

    fun random() = apply()

}