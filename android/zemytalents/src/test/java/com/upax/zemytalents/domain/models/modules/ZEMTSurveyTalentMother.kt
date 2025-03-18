package com.upax.zemytalents.domain.models.modules

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTSurveyTalentMother {

    fun apply(
        id: Int = ZEMTRandomValuesUtil.getInt(),
        name: String = ZEMTRandomValuesUtil.getString(),
        order: Int = ZEMTRandomValuesUtil.getInt(),
        description: String = ZEMTRandomValuesUtil.getString(),
        questions: List<ZEMTSurveyTalentQuestion> = ZEMTRandomValuesUtil.getRandomIntRange().map {
            ZEMTSurveyTalentQuestionMother.random()
        }
    ): ZEMTSurveyTalent {
        return ZEMTSurveyTalent(
            id = id,
            name = name,
            order = order,
            description = description,
            questions = questions
        )
    }

    fun random() = apply()

    fun withOneQuestion(id: Int) =
        apply(id = id, questions = listOf(ZEMTSurveyTalentQuestionMother.withFiveAnswers()))

    fun withOneQuestionWithOneAnswerChecked(id: Int) = apply(
        id = id,
        questions = listOf(ZEMTSurveyTalentQuestionMother.withFourAnswersUncheckedAndOneAnswerChecked())
    )

    fun withOneQuestionWithAllAnswersUnchecked(id: Int) = apply(
        id = id,
        questions = listOf(ZEMTSurveyTalentQuestionMother.withFiveAnswersUnchecked())
    )


    fun withOneQuestionWithAllAnswersChecked(id: Int) = apply(
        id = id,
        questions = listOf(ZEMTSurveyTalentQuestionMother.withFiveAnswersChecked())
    )


}


