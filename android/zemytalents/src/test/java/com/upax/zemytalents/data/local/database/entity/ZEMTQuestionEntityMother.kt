package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTQuestionEntityMother {

    fun apply(
        questionId: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        surveyId: Int = ZEMTRandomValuesUtil.getInt(),
        position: Int = ZEMTRandomValuesUtil.getInt(),
        groupQuestionIndex: Int = ZEMTRandomValuesUtil.getInt(),
        lastSeen: Long = ZEMTRandomValuesUtil.getInt().toLong()
    ): ZEMTQuestionDiscoverEntity {
        return ZEMTQuestionDiscoverEntity(
            questionId = questionId,
            text = text,
            surveyId = surveyId,
            order = position,
            groupQuestionIndex = groupQuestionIndex,
            lastSeen = lastSeen
        )
    }

    fun random() = apply()

}