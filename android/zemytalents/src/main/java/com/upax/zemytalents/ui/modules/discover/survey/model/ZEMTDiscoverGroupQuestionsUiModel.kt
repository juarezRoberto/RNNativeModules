package com.upax.zemytalents.ui.modules.discover.survey.model

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal data class ZEMTDiscoverGroupQuestionsUiModel(
    val index: ZEMTGroupQuestionIndexDiscover,
    val leftQuestion: ZEMTDiscoverQuestionUiModel,
    val rightQuestion: ZEMTDiscoverQuestionUiModel,
    val answers: List<ZEMTDiscoverAnswerUiModel>
) {

    val neutralAnswer = answers.getOrNull(2)

}