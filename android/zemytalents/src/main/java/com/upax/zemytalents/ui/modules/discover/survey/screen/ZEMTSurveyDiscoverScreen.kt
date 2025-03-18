package com.upax.zemytalents.ui.modules.discover.survey.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverUiState
import com.upax.zemytalents.ui.modules.discover.survey.composables.ZEMTAnswerList
import com.upax.zemytalents.ui.modules.discover.survey.composables.ZEMTCircleLoading
import com.upax.zemytalents.ui.modules.discover.survey.composables.splitlayout.ZEMTHorizontalSplitLayout
import com.upax.zemytalents.ui.modules.discover.survey.composables.ZEMTLeftRightTexts
import com.upax.zemytalents.ui.modules.discover.survey.composables.ZEMTProgressBar
import com.upax.zemytalents.ui.modules.discover.survey.composables.splitlayout.rememberBackgroundColorsStateHolder
import com.upax.zemytalents.ui.modules.discover.survey.mock.ZEMTDiscoverMockData
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerSide
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTSurveyDiscoverScreen(
    uiState: ZEMTSurveyDiscoverUiState,
    onAnswerSelected: (answer: ZEMTDiscoverAnswerUiModel) -> Unit,
    onGroupQuestionsSkip: () -> Unit
) {

    val splitLayoutStateHolder = rememberBackgroundColorsStateHolder()

    val timerStateHolder = rememberTimerStateHolder(
        durationMillis = uiState.timeInSecondsToResponseQuestions * 1000,
        onTimerFinished = { onGroupQuestionsSkip.invoke() }
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, uiState.timerShouldBeRunning) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (uiState.timerShouldBeRunning) timerStateHolder.resumeTimer()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    if (uiState.timerShouldBeRunning) timerStateHolder.pauseTimer()
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(uiState.currentGroupQuestions?.index, uiState.timerShouldBeRunning) {
        if (uiState.currentGroupQuestions != null && uiState.timerShouldBeRunning) {
            timerStateHolder.restartTimer()
        } else {
            timerStateHolder.cancelTimer()
        }
    }

    ZEMTHorizontalSplitLayout(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        backgroundContent = splitLayoutStateHolder.backgroundContent.value
    ) {

        Column(modifier = Modifier.weight(0.33f, true)) {

            ZEMTProgressBar(
                currentProgress = uiState.totalGroupQuestionsAnswered ?: 0,
                maxProgress = uiState.totalGroupQuestions ?: 0,
                modifier = Modifier
                    .padding(top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium))
            )

            VerticalDivider(modifier = Modifier.weight(0.18f, true))

            AnimatedVisibility(
                visible = uiState.isTimerVisible,
                modifier = Modifier
                    .weight(0.82f, true)
                    .align(Alignment.CenterHorizontally),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                ZEMTCircleLoading(progress = timerStateHolder.timer.value)
            }
        }

        VerticalDivider(modifier = Modifier.weight(0.05f, true))

        ZEMTLeftRightTexts(
            leftText = uiState.currentLeftQuestion?.text.orEmpty(),
            rightText = uiState.currentRightQuestion?.text.orEmpty(),
            modifier = Modifier
                .weight(0.22f, true)
                .padding(bottom = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium))
        )

        ZEMTAnswerList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f, true),
            onAnswerSelected = {
                onAnswerSelected.invoke(it)
                splitLayoutStateHolder.resetBackgroundContent()
            },
            onAnswerPreselected = {
                when (it.side) {
                    ZEMTDiscoverAnswerSide.LEFT -> {
                        splitLayoutStateHolder.setLeftBackgroundContent(it.color)
                    }

                    ZEMTDiscoverAnswerSide.RIGHT -> {
                        splitLayoutStateHolder.setRightBackgroundContent(it.color)
                    }

                    ZEMTDiscoverAnswerSide.MIDDLE -> {
                        splitLayoutStateHolder.setFullBackgroundContent(it.color)
                    }
                }
            },
            answers = uiState.currentAnswers ?: listOf(),
        )
    }
}

@Preview
@Composable
private fun ZEMTSurveyDiscoverScreenPreview() {
    var uiState by remember { mutableStateOf(ZEMTDiscoverMockData.mockUiState) }

    fun updateCurrentGroupQuestionIndex() {
        uiState = uiState.copy(
            totalGroupQuestionsAnswered = (uiState.totalGroupQuestionsAnswered ?: 0) + 1
        )
    }

    ZEMTSurveyDiscoverScreen(
        uiState = uiState,
        onAnswerSelected = { updateCurrentGroupQuestionIndex() },
        onGroupQuestionsSkip = { }
    )
}