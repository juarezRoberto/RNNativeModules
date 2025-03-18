package com.upax.zemytalents.ui.modules.apply.survey.model

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyApplyQuestionUiModelMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        header: String = ZEMTRandomValuesUtil.getString(),
        text: String = ZEMTRandomValuesUtil.getString(),
        answerOptions: List<ZEMTSurveyApplyAnswerOptionUiModel> = ZEMTRandomValuesUtil
            .getRandomIntRange().map { ZEMTSurveyApplyAnswerOptionUiModelMother.random() },
        isCompleted: Boolean = ZEMTRandomValuesUtil.getBoolean()
    ): ZEMTSurveyApplyQuestionUiModel {
        return ZEMTSurveyApplyQuestionUiModel(
            id = id,
            order = order,
            header = header,
            text = text,
            answerOptions = answerOptions,
            isCompleted = isCompleted
        )
    }

    fun random() = apply()

}