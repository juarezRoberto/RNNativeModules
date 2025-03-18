package com.upax.zemytalents.data.local.database.entity

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTBreakDiscoverEntity
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTBreakDiscoverEntityMother {

    fun apply(
        groupQuestionIndex: Int = ZEMTRandomValuesUtil.getInt(),
        surveyId: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        seen: Boolean = ZEMTRandomValuesUtil.getBoolean(),
    ): ZEMTBreakDiscoverEntity {
        return ZEMTBreakDiscoverEntity(
            groupQuestionIndex = groupQuestionIndex,
            surveyId = surveyId,
            text = text,
            seen = seen
        )
    }

    fun random() = apply()

}