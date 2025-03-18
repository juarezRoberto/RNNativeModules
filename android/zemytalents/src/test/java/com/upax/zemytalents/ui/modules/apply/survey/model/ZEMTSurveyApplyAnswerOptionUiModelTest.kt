package com.upax.zemytalents.ui.modules.apply.survey.model

import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.Status
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTSurveyApplyAnswerOptionUiModelTest {

    @Test
    fun `isChecked returns false when status is equals to UNCHECKED`() {
        val answer = ZEMTSurveyApplyAnswerOptionUiModelMother.apply(
            status = Status.UNCHECKED
        )

        assertFalse(answer.isChecked)
    }

    @Test
    fun `isChecked returns true when status is different to UNCHECKED`() {
        val answer = ZEMTSurveyApplyAnswerOptionUiModelMother.apply(
            status = Status.CHECK_POSITIVE
        )

        assertTrue(answer.isChecked)
    }

}