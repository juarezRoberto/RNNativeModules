package com.upax.zemytalents.ui.modules.discover

import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscoverMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverUiState
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverGroupQuestionsUiModelMother
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTSurveyDiscoverUiStateTest {

    @Test
    fun `timerShouldBeRunning return true when timer is visible and alert is not visible`() {
        val uiState = ZEMTSurveyDiscoverUiState(isTimerVisible = true, isAlertVisible = false)
        assertTrue(uiState.timerShouldBeRunning)
    }

    @Test
    fun `timerShouldBeRunning return false when timer is not visible`() {
        val uiState = ZEMTSurveyDiscoverUiState(isTimerVisible = false)
        assertFalse(uiState.timerShouldBeRunning)
    }

    @Test
    fun `timerShouldBeRunning return false when alert is visible`() {
        val uiState = ZEMTSurveyDiscoverUiState(isAlertVisible = true)
        assertFalse(uiState.timerShouldBeRunning)
    }

    @Test
    fun `isLastGroupQuestions return true when totalGroupQuestionsAnswered plus one is equal to totalGroupQuestions`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = 9, totalGroupQuestions = 10
        )
        assertTrue(uiState.isLastGroupQuestions)
    }

    @Test
    fun `isLastGroupQuestions return false when totalGroupQuestionsAnswered plus one is less than totalGroupQuestions`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = 8, totalGroupQuestions = 10
        )
        assertFalse(uiState.isLastGroupQuestions)
    }

    @Test
    fun `isLastGroupQuestions return false when totalGroupQuestionsAnswered is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = null, totalGroupQuestions = ZEMTRandomValuesUtil.getInt()
        )
        assertFalse(uiState.isLastGroupQuestions)
    }

    @Test
    fun `isLastGroupQuestions return false when totalGroupQuestions is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestions = null)
        assertFalse(uiState.isLastGroupQuestions)
    }

    @Test
    fun `userHasProgress should be false when totalGroupQuestionsAnswered is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestionsAnswered = null)
        assertFalse(uiState.userHasProgress)
    }

    @Test
    fun `userHasProgress should be false when totalGroupQuestionsAnswered is 0`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestionsAnswered = 0)
        assertFalse(uiState.userHasProgress)
    }

    @Test
    fun `userHasProgress should be true when totalGroupQuestionsAnswered is greater than 0`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = ZEMTRandomValuesUtil.getInt(min = 1, max = Int.MAX_VALUE)
        )
        assertTrue(uiState.userHasProgress)
    }

    @Test
    fun `userHasFinishSurvey should be false when totalGroupQuestionsAnswered is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestionsAnswered = null)
        assertFalse(uiState.userHasFinishSurvey)
    }

    @Test
    fun `userHasFinishSurvey should be false when totalGroupQuestions is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestions = null)
        assertFalse(uiState.userHasFinishSurvey)
    }

    @Test
    fun `userHasFinishSurvey should be false when totalGroupQuestions is 0`() {
        val uiState = ZEMTSurveyDiscoverUiState(totalGroupQuestions = 0)
        assertFalse(uiState.userHasFinishSurvey)
    }

    @Test
    fun `userHasFinishSurvey should be false when totalGroupQuestionsAnswered is less than totalGroupQuestions`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = 9,
            totalGroupQuestions = 10
        )
        assertFalse(uiState.userHasFinishSurvey)
    }

    @Test
    fun `userHasFinishSurvey should be true when totalGroupQuestionsAnswered equal to totalGroupQuestions`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = 10,
            totalGroupQuestions = 10
        )
        assertTrue(uiState.userHasFinishSurvey)
    }

    @Test
    fun `userHasFinishSurvey should be true when totalGroupQuestionsAnswered greater than totalGroupQuestions`() {
        val uiState = ZEMTSurveyDiscoverUiState(
            totalGroupQuestionsAnswered = 11,
            totalGroupQuestions = 10
        )
        assertTrue(uiState.userHasFinishSurvey)
    }

    @Test
    fun `currentGroupQuestionHaveBreak should be false if nextBreakToShow is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(nextBreakToShow = null)
        assertFalse(uiState.currentGroupQuestionHaveBreak)
    }

    @Test
    fun `currentGroupQuestionHaveBreak should be false if currentGroupQuestions is null`() {
        val uiState = ZEMTSurveyDiscoverUiState(currentGroupQuestions = null)
        assertFalse(uiState.currentGroupQuestionHaveBreak)
    }

    @Test
    fun `currentGroupQuestionHaveBreak should be false if currentGroupQuestions index is different to nextBreakToShow index`() {
        val groupQuestions = ZEMTDiscoverGroupQuestionsUiModelMother
            .apply(index = ZEMTGroupQuestionIndexDiscover(1))
        val nextBreakToShow = ZEMTBreakDiscoverMother.apply(
            indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(2)
        )
        val uiState = ZEMTSurveyDiscoverUiState(
            currentGroupQuestions = groupQuestions,
            nextBreakToShow = nextBreakToShow
        )
        assertFalse(uiState.currentGroupQuestionHaveBreak)
    }

    @Test
    fun `currentGroupQuestionHaveBreak should be true if currentGroupQuestions index is equals to nextBreakToShow index`() {
        val groupQuestions = ZEMTDiscoverGroupQuestionsUiModelMother
            .apply(index = ZEMTGroupQuestionIndexDiscover(2))
        val nextBreakToShow = ZEMTBreakDiscoverMother.apply(
            indexGroupQuestion = ZEMTGroupQuestionIndexDiscover(2)
        )
        val uiState = ZEMTSurveyDiscoverUiState(
            currentGroupQuestions = groupQuestions,
            nextBreakToShow = nextBreakToShow
        )
        assertTrue(uiState.currentGroupQuestionHaveBreak)
    }

}