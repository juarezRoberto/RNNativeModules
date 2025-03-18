package com.upax.zemytalents.ui.modules.apply.survey.model

import androidx.annotation.DrawableRes
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyApplyTalentUiModelMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        name: String = ZEMTRandomValuesUtil.getString(),
        finished: Boolean = ZEMTRandomValuesUtil.getBoolean(),
        selected: Boolean = ZEMTRandomValuesUtil.getBoolean(),
        lottieUrl: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        @DrawableRes icon: Int = ZEMTRandomValuesUtil.getInt(),
        question: ZEMTSurveyApplyQuestionUiModel = ZEMTSurveyApplyQuestionUiModelMother.random()
    ): ZEMTSurveyApplyTalentUiModel {
        return ZEMTSurveyApplyTalentUiModel(
            id = id,
            name = name,
            finished = finished,
            selected = selected,
            lottieUrl = lottieUrl,
            order = order,
            icon = icon,
            question = question
        )
    }

    fun random() = apply()

}