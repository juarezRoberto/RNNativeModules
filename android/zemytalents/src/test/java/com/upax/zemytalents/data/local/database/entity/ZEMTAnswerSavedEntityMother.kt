package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerSavedDiscoverEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal data object ZEMTAnswerSavedEntityMother {

    fun apply(
        questionId: Int = ZEMTRandomValuesUtil.getInt(),
        answerOptionId: Int = ZEMTRandomValuesUtil.getInt(),
        date: String = ZEMTRandomValuesUtil.getString(),
        text: String = ZEMTRandomValuesUtil.getString(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
        groupQuestionIndex: Int = ZEMTRandomValuesUtil.getInt(),
        latitude: String = ZEMTRandomValuesUtil.getString(),
        longitude: String = ZEMTRandomValuesUtil.getString(),
    ): ZEMTAnswerSavedDiscoverEntity {
        return ZEMTAnswerSavedDiscoverEntity(
            questionId = questionId,
            answerOptionId = answerOptionId,
            date = date,
            latitude = latitude,
            longitude = longitude,
            text = text,
            value = value,
            groupQuestionIndex = groupQuestionIndex
        )
    }

    fun random() = apply()

}