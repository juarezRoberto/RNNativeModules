package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTQuestionDiscoverMother {

    fun apply(
        id: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        answers: List<ZEMTAnswerOptionDiscover> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTAnswerOptionDiscoverMother.random()
        }
    ): ZEMTQuestionDiscover {
        return ZEMTQuestionDiscover(
            id,
            text,
            order,
            answers
        )
    }

    fun random() = apply()

}