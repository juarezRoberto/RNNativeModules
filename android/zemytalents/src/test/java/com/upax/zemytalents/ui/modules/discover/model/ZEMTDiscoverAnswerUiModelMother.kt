package com.upax.zemytalents.ui.modules.discover.model

import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerSide
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTDiscoverAnswerUiModelMother {

    fun apply(
        id: ZEMTAnswerId = ZEMTAnswerId(ZEMTRandomValuesUtil.getInt()),
        questionId: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        enabled: Boolean = ZEMTRandomValuesUtil.getBoolean(),
        iconSelected: Int = ZEMTRandomValuesUtil.getInt(),
        iconUnselected: Int = ZEMTRandomValuesUtil.getInt(),
        color: Int = ZEMTRandomValuesUtil.getInt(),
        side: ZEMTDiscoverAnswerSide = ZEMTDiscoverAnswerSide.entries.random()
    ): ZEMTDiscoverAnswerUiModel {
        return ZEMTDiscoverAnswerUiModel(
            id = id,
            questionId = questionId,
            text = text,
            order = order,
            enabled = enabled,
            iconUnselected = iconUnselected,
            iconSelected = iconSelected,
            color = color,
            side = side
        )
    }

    fun random() = apply()

}