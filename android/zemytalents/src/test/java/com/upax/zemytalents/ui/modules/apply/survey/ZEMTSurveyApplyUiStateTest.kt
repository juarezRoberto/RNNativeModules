package com.upax.zemytalents.ui.modules.apply.survey

import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyTalentUiModelMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTSurveyApplyUiStateTest {

    @Test
    fun `default constructor should have empty dominantTalents`() {
        val uiState = ZEMTSurveyApplyUiState()
        assertTrue(uiState.dominantTalents.isEmpty())
    }

    @Test
    fun `default constructor should have empty surveyTalents`() {
        val uiState = ZEMTSurveyApplyUiState()
        assertTrue(uiState.surveyTalents.isEmpty())
    }

    @Test
    fun `currentTalent should be talent with selected true`() {
        val idTalentSelected = 11
        val uiState = ZEMTSurveyApplyUiState(
            surveyTalents = listOf(
                ZEMTSurveyApplyTalentUiModelMother.apply(selected = false, id = 10),
                ZEMTSurveyApplyTalentUiModelMother.apply(selected = true, id = idTalentSelected),
                ZEMTSurveyApplyTalentUiModelMother.apply(selected = false, id = 12),
            )
        )
        assertEquals(idTalentSelected, uiState.currentTalent?.id)
    }

    @Test
    fun `totalTalentsPendingToAnswer should return total talents with finished false minus one (the selected one)`() {
            val uiState = ZEMTSurveyApplyUiState(
                surveyTalents = listOf(
                    ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                    ZEMTSurveyApplyTalentUiModelMother.apply(finished = false, selected = true),
                    ZEMTSurveyApplyTalentUiModelMother.apply(finished = false),
                    ZEMTSurveyApplyTalentUiModelMother.apply(finished = false),
                )
            )
            assertEquals(2, uiState.totalTalentsPendingToAnswer)
        }

    @Test
    fun `nextTalent should be the first talent with finished false`() {
        val nextTalentId = 22
        val uiState = ZEMTSurveyApplyUiState(
            surveyTalents = listOf(
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true, id = 1),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true, id = 2),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = false, id = nextTalentId),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = false, id = 3),
            )
        )
        assertEquals(nextTalentId, uiState.nextTalent?.id)
    }

    @Test
    fun `when all talents have been finished nextTalent return null`() {
        val uiState = ZEMTSurveyApplyUiState(
            surveyTalents = listOf(
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
            )
        )
        assertNull(uiState.nextTalent)
    }

    @Test
    fun `if any talent has not been finished then surveyFinished should be false`() {
        val uiState = ZEMTSurveyApplyUiState(
            surveyTalents = listOf(
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = false),
            )
        )
        assertFalse(uiState.surveyFinished)
    }

    @Test
    fun `when all talents have been finished then surveyFinished should be true`() {
        val uiState = ZEMTSurveyApplyUiState(
            surveyTalents = listOf(
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
                ZEMTSurveyApplyTalentUiModelMother.apply(finished = true),
            )
        )
        assertTrue(uiState.surveyFinished)
    }

    @Test
    fun `surveyFinished should be false when surveyTalents is empty list`() {
        val uiState = ZEMTSurveyApplyUiState(surveyTalents = emptyList())
        assertFalse(uiState.surveyFinished)
    }

}