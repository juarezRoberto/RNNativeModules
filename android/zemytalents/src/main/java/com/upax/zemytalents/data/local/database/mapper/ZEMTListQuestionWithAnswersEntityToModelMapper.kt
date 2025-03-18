package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.embedded.ZEMTQuestionWithAnswers
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover

internal class ZEMTListQuestionWithAnswersEntityToModelMapper(
    private val questionWithAnswersToModelMapper: ZEMTQuestionWithAnswersEntityToModelMapper
) : Function1<List<ZEMTQuestionWithAnswers>?, ZEMTGroupQuestionsDiscover?> {

    override operator fun invoke(
        questionWithAnswers: List<ZEMTQuestionWithAnswers>?
    ): ZEMTGroupQuestionsDiscover? {
        if (questionWithAnswers.isNullOrEmpty() || questionWithAnswers.count() < 2) return null
        val questions = questionWithAnswers.map(questionWithAnswersToModelMapper)
        return ZEMTGroupQuestionsDiscover(
            index = ZEMTGroupQuestionIndexDiscover(questionWithAnswers[0].question.groupQuestionIndex),
            leftQuestion = questions[0],
            rightQuestion = questions[1],
        )
    }

}