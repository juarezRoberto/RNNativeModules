package com.upax.zemytalents.domain.models.modules

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyTalentQuestionMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        header: String = ZEMTRandomValuesUtil.getString(),
        text: String = ZEMTRandomValuesUtil.getString(),
        answerOptions: List<ZEMTSurveyTalentQuestionOption> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTSurveyTalentQuestionOptionMother.random() }
    ): ZEMTSurveyTalentQuestion {
        return ZEMTSurveyTalentQuestion(
            id = id,
            order = order,
            header = header,
            text = text,
            answerOptions = answerOptions
        )
    }

    fun random() = apply()

    fun withFiveAnswers() = apply(answerOptions = (1..5).mapIndexed { index, _ ->
        ZEMTSurveyTalentQuestionOptionMother.apply(id = index)
    })

    fun withFiveAnswersUnchecked() = apply(answerOptions = (1..5).mapIndexed { index, _ ->
        ZEMTSurveyTalentQuestionOptionMother.unchecked(id = index)
    })

    fun withFiveAnswersChecked() = apply(answerOptions = (1..5).mapIndexed { index, _ ->
        ZEMTSurveyTalentQuestionOptionMother.positiveChecked(id = index)
    })

    fun withFourAnswersUncheckedAndOneAnswerChecked() =
        apply(answerOptions = (1..5).mapIndexed { index, _ ->
            if (index == 1) {
                ZEMTSurveyTalentQuestionOptionMother.positiveChecked(id = index)
            } else {
                ZEMTSurveyTalentQuestionOptionMother.unchecked(id = index)
            }
        })

}