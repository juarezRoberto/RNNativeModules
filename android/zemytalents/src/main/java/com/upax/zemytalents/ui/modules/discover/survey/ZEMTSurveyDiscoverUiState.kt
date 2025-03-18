package com.upax.zemytalents.ui.modules.discover.survey

import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverGroupQuestionsUiModel

internal data class ZEMTSurveyDiscoverUiState(
    val currentGroupQuestions: ZEMTDiscoverGroupQuestionsUiModel? = null,
    val totalGroupQuestionsAnswered: Int? = null,
    val totalGroupQuestions: Int? = null,
    val isTimerVisible: Boolean = true,
    val isAlertVisible: Boolean = false,
    val nextBreakToShow: ZEMTBreakDiscover? = null
) {

    val timerShouldBeRunning = isTimerVisible && !isAlertVisible
    val isLastGroupQuestions: Boolean
        get() {
            val currentIndex = totalGroupQuestionsAnswered ?: return false
            val maxIndex = totalGroupQuestions ?: return false
            return maxIndex - currentIndex == 1
        }
    val userHasProgress = totalGroupQuestionsAnswered.orZero() > 0
    val userHasFinishSurvey: Boolean
        get() {
            val currentIndex = totalGroupQuestionsAnswered ?: return false
            val maxIndex = totalGroupQuestions ?: return false
            if (maxIndex == 0) return false
            return currentIndex >= maxIndex
        }
    val timeInSecondsToResponseQuestions: Int = 35
    val currentLeftQuestion = currentGroupQuestions?.leftQuestion
    val currentRightQuestion = currentGroupQuestions?.rightQuestion
    val currentAnswers = currentGroupQuestions?.answers
    val currentGroupQuestionHaveBreak: Boolean
        get() {
            val nextBreakToShow = nextBreakToShow ?: return false
            val indexCurrentGroupQuestions = currentGroupQuestions?.index ?: return false
            return nextBreakToShow.indexGroupQuestion == indexCurrentGroupQuestions
        }

}