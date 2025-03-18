package com.upax.zemytalents.domain.models.modules.discover

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTAnswerSavedMother {

    fun apply(
        id: ZEMTAnswerId = ZEMTAnswerId(ZEMTRandomValuesUtil.getInt()),
        questionId: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        groupQuestionIndex: Int = ZEMTRandomValuesUtil.getInt(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
        date: String = ZEMTRandomValuesUtil.getString(),
        location: ZEMTLocation = ZEMTLocation(
            ZEMTRandomValuesUtil.getString(),
            ZEMTRandomValuesUtil.getString()
        ),
    ): ZEMTAnswerSavedDiscover {
        return ZEMTAnswerSavedDiscover(
            id = id,
            questionId = questionId,
            groupQuestionIndex = groupQuestionIndex,
            value = value,
            date = date,
            location = location
        )
    }

    fun random() = apply()

}