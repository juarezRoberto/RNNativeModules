package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTQuestionMother {

    fun apply(
        id: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        answers: List<ZEMTAnswerOptionDiscover> = ZEMTRandomValuesUtil.getRandomIntRange(1, 3)
            .map {
                ZEMTAnswerOptionMother.apply(
                    order = it,
                    value = if (it == 3) 0 else ZEMTRandomValuesUtil.getInt()
                )
            }
    ): ZEMTQuestionDiscover {
        return ZEMTQuestionDiscover(
            id = id,
            text = text,
            order = order,
            answers = answers
        )
    }

    fun random() = apply()

}