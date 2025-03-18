package com.upax.zemytalents.domain.models.modules.apply

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyApplyAnswerSavedMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        questionId: Int = ZEMTRandomValuesUtil.getInt(),
        status: ZEMTSurveyApplyAnswerStatus = ZEMTSurveyApplyAnswerStatus.entries.random(),
        location: ZEMTLocation = ZEMTLocation(
            ZEMTRandomValuesUtil.getString(),
            ZEMTRandomValuesUtil.getString()
        ),
        date: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt()
    ): ZEMTSurveyApplyAnswerSaved {
        return ZEMTSurveyApplyAnswerSaved(
            id = id,
            questionId = questionId,
            status = status,
            location = location,
            date = date,
            order = order
        )
    }

    fun random() = apply()

}