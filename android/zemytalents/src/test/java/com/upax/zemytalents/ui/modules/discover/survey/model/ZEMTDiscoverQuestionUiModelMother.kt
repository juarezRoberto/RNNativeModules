package com.upax.zemytalents.ui.modules.discover.survey.model

import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTDiscoverQuestionUiModelMother {

    fun random(
        id: ZEMTQuestionId = ZEMTQuestionId(ZEMTRandomValuesUtil.getInt()),
        text: String = ZEMTRandomValuesUtil.getString()
    ): ZEMTDiscoverQuestionUiModel {
        return ZEMTDiscoverQuestionUiModel(id = id, text = text)
    }

    fun apply() = random()

}