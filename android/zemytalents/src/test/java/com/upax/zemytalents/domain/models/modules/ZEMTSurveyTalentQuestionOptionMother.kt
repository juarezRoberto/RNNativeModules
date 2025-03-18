package com.upax.zemytalents.domain.models.modules

import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyTalentQuestionOptionMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
        status: ZEMTSurveyApplyAnswerStatus =
            ZEMTSurveyApplyAnswerStatus.UNCHECKED
    ): ZEMTSurveyTalentQuestionOption {
        return ZEMTSurveyTalentQuestionOption(
            id = id,
            order = order,
            text = text,
            value = value,
            status = status
        )
    }

    fun random() = apply()

    fun unchecked(id: Int) = apply(id = id, status = ZEMTSurveyApplyAnswerStatus.UNCHECKED)

    fun positiveChecked(id: Int) =
        apply(id = id, status = ZEMTSurveyApplyAnswerStatus.CHECK_POSITIVE)

}