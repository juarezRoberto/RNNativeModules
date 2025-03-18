package com.upax.zemytalents.ui.modules.discover.survey.model

import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.ui.modules.discover.model.ZEMTDiscoverAnswerUiModelMother
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTDiscoverGroupQuestionsUiModelMother {

    fun apply(
        index: ZEMTGroupQuestionIndexDiscover = ZEMTGroupQuestionIndexDiscover(ZEMTRandomValuesUtil.getInt()),
        leftQuestion: ZEMTDiscoverQuestionUiModel = ZEMTDiscoverQuestionUiModelMother.random(),
        rightQuestion: ZEMTDiscoverQuestionUiModel = ZEMTDiscoverQuestionUiModelMother.random(),
        answers: List<ZEMTDiscoverAnswerUiModel> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTDiscoverAnswerUiModelMother.random()
        }
    ): ZEMTDiscoverGroupQuestionsUiModel {
        return ZEMTDiscoverGroupQuestionsUiModel(
            index = index,
            leftQuestion = leftQuestion,
            rightQuestion = rightQuestion,
            answers = answers
        )
    }

    fun random() = apply()

}