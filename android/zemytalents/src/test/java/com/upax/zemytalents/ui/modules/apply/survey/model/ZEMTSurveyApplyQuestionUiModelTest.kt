package com.upax.zemytalents.ui.modules.apply.survey.model

import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

internal class ZEMTSurveyApplyQuestionUiModelTest {

    @Test
    fun `currentAnswer should be equals to first answer with UNCHECKED status`() {
        val idCurrentAnswer = 999
        val answerOptions = listOf(
            ZEMTSurveyApplyAnswerOptionUiModelMother.apply(status = Status.CHECK_POSITIVE, id = 1),
            ZEMTSurveyApplyAnswerOptionUiModelMother.apply(
                status = Status.UNCHECKED,
                id = idCurrentAnswer
            ),
            ZEMTSurveyApplyAnswerOptionUiModelMother.apply(status = Status.UNCHECKED, id = 2)
        )
        val question = ZEMTSurveyApplyQuestionUiModelMother.apply(answerOptions = answerOptions)

        assertEquals(idCurrentAnswer, question.currentAnswer?.id)
    }

    @Test
    fun `allAnswersChecked returns false when at least one answer with status UNCHECKED`() {
            val answerOptions = listOf(
                ZEMTSurveyApplyAnswerOptionUiModelMother.apply(status = Status.CHECK_POSITIVE),
                ZEMTSurveyApplyAnswerOptionUiModelMother.apply(status = Status.CHECK_POSITIVE),
                ZEMTSurveyApplyAnswerOptionUiModelMother.apply(status = Status.UNCHECKED)
            )
            val question = ZEMTSurveyApplyQuestionUiModelMother.apply(answerOptions = answerOptions)

            assertFalse(question.allAnswersChecked)
        }

}