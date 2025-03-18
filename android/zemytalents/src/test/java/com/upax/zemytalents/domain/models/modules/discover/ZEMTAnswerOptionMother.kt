package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAnswerOptionMother {

    fun apply(
        id: ZEMTAnswerId = ZEMTAnswerId(ZEMTRandomValuesUtil.getInt()),
        questionId: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
    ): ZEMTAnswerOptionDiscover {
        return ZEMTAnswerOptionDiscover(
            id = id,
            questionId = questionId,
            text = text,
            order = order,
            value = value
        )
    }

    fun random() = apply()

}