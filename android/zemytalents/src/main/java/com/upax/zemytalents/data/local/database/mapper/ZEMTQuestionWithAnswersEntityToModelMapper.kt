package com.upax.zemytalents.data.local.database.mapper

import com.upax.zemytalents.data.local.database.embedded.ZEMTQuestionWithAnswers
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerOptionDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionDiscover
import com.upax.zemytalents.domain.models.modules.ZEMTAnswerId
import com.upax.zemytalents.domain.models.modules.discover.ZEMTQuestionId

internal class ZEMTQuestionWithAnswersEntityToModelMapper :
    Function1<ZEMTQuestionWithAnswers, ZEMTQuestionDiscover> {

    override operator fun invoke(questionWithAnswers: ZEMTQuestionWithAnswers): ZEMTQuestionDiscover {
        return ZEMTQuestionDiscover(
            id = ZEMTQuestionId(questionWithAnswers.question.questionId),
            text = questionWithAnswers.question.text,
            order = questionWithAnswers.question.order,
            answers = mapAnswers(questionWithAnswers.answers)
        )
    }

    private fun mapAnswers(answers: List<ZEMTAnswerOptionDiscoverEntity>): List<ZEMTAnswerOptionDiscover> {
        return answers.sortedBy { it.order }.map {
            ZEMTAnswerOptionDiscover(
                id = ZEMTAnswerId(it.answerOptionId),
                questionId = ZEMTQuestionId(it.questionId),
                text = it.text,
                order = it.order,
                value = it.value
            )
        }
    }

}