package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyTalentOptionResponse
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyTalentQuestionResponse
import com.upax.zemytalents.data.remote.responses.ZEMTSurveyTalentsWrapper
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestion
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalentQuestionOption
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus

internal class ZEMTSurveyTalentsResponseToModelMapper :
    Function1<ZEMTSurveyTalentsWrapper, List<ZEMTSurveyTalent>> {

    override fun invoke(surveyTalent: ZEMTSurveyTalentsWrapper): List<ZEMTSurveyTalent> {
        return surveyTalent.talents?.mapIndexed { index, talent ->
            ZEMTSurveyTalent(
                id = talent.id.orZero(),
                name = talent.name.orEmpty(),
                order = index,
                description = talent.description.orEmpty(),
                questions = talent.questions.orEmpty().map { mapQuestion(it) }
            )
        }.orEmpty()
    }

    private fun mapQuestion(question: ZEMTSurveyTalentQuestionResponse): ZEMTSurveyTalentQuestion {
        return ZEMTSurveyTalentQuestion(
            id = question.id.orZero(),
            order = question.order.orZero(),
            header = question.header.orEmpty(),
            text = question.text.orEmpty(),
            answerOptions = question.answerOptions.orEmpty().sortedBy { it.order }
                .map { mapAnswer(it) }
        )
    }

    private fun mapAnswer(answer: ZEMTSurveyTalentOptionResponse): ZEMTSurveyTalentQuestionOption {
        return ZEMTSurveyTalentQuestionOption(
            id = answer.id.orZero(),
            order = answer.order.orZero(),
            text = answer.text.orEmpty(),
            value = answer.value.orZero(),
            status = ZEMTSurveyApplyAnswerStatus.UNCHECKED
        )
    }

}