package com.upax.zemytalents.ui.modules.apply.survey.model

import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.Status
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyApplyAnswerOptionUiModelMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        questionId: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        text: String = ZEMTRandomValuesUtil.getString(),
        value: Int = ZEMTRandomValuesUtil.getInt(),
        status: Status = Status.entries.random()
    ): ZEMTSurveyApplyAnswerOptionUiModel {
        return ZEMTSurveyApplyAnswerOptionUiModel(
            id = id,
            questionId = questionId,
            order = order,
            text = text,
            value = value,
            status = status
        )
    }

    fun random() = apply()

}