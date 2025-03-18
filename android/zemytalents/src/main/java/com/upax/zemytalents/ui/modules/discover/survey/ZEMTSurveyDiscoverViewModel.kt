package com.upax.zemytalents.ui.modules.discover.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.error.ZEMTSaveAnswerError
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakDiscover
import com.upax.zemytalents.domain.models.modules.discover.ZEMTGroupQuestionsDiscover
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverBreaksRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverDownloader
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverReminder
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSaveAnswerDiscoverUserCase
import com.upax.zemytalents.domain.usecases.ZEMTSkipGroupQuestionsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersDiscoverUseCase
import com.upax.zemytalents.ui.modules.discover.survey.mapper.ZEMTDiscoverGroupQuestionsToUiModelMapper
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTSurveyDiscoverViewModel(
    private val groupQuestionsRepository: ZEMTGroupQuestionsDiscoverRepository,
    private val saveAnswerUseCase: ZEMTSaveAnswerDiscoverUserCase,
    private val surveyDownloader: ZEMTSurveyDiscoverDownloader,
    private val skipGroupQuestionsUseCase: ZEMTSkipGroupQuestionsUseCase,
    private val surveyReminder: ZEMTSurveyDiscoverReminder,
    private val synchronizeAnswersUseCase: ZEMTSynchronizeAnswersDiscoverUseCase,
    private val locationUpdater: ZEMTLocationUpdater,
    private val breaksRepository: ZEMTSurveyDiscoverBreaksRepository,
    discoverGroupQuestionToUiModelMapper: ZEMTDiscoverGroupQuestionsToUiModelMapper
) : ViewModel() {

    private val maxNumberOfNeutralAnswersReachedState = MutableStateFlow(false)
    private val isTimerVisibleState = MutableStateFlow(true)
    private var isFirstAlertEmitted = false

    private val _alertsState = MutableStateFlow<ZEMTSurveyDiscoverAlerts?>(null)
    val alertsState = _alertsState.asStateFlow()

    val uiState = combine(
        groupQuestionsRepository.totalGroupQuestionsAnswered,
        flow { emit(groupQuestionsRepository.getTotalGroupQuestions()) },
        groupQuestionsRepository.currentGroupQuestions,
        maxNumberOfNeutralAnswersReachedState,
        isTimerVisibleState,
        _alertsState,
        breaksRepository.getNextBreak()
    ) { flows ->
        ZEMTSurveyDiscoverUiState(
            currentGroupQuestions = discoverGroupQuestionToUiModelMapper.invoke(
                flows[2] as ZEMTGroupQuestionsDiscover?,
                flows[3] as Boolean
            ),
            totalGroupQuestionsAnswered = flows[0] as Int,
            totalGroupQuestions = flows[1] as Int,
            isTimerVisible = flows[4] as Boolean,
            isAlertVisible = flows[5] as ZEMTSurveyDiscoverAlerts? != null,
            nextBreakToShow = flows[6] as ZEMTBreakDiscover?
        )
    }.onStart {
        downloadSurveyIfNecessary()
    }.onEach {
        validateProgressAlerts(it)
    }.onEach {
        validateBreakAlert(it)
    }.stateIn(viewModelScope, WhileSubscribed(5_000), ZEMTSurveyDiscoverUiState())

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState = _isLoadingState.asStateFlow()

    private val _surveyFinishedState = MutableStateFlow(false)
    val surveyFinishedState = _surveyFinishedState.asStateFlow()

    private suspend fun downloadSurveyIfNecessary() {
        if (!surveyDownloader.needToDownload()) return
        _isLoadingState.update { true }
        when (surveyDownloader.download()) {
            is ZEMTResult.Success -> Unit
            is ZEMTResult.Error -> _alertsState.update { ZEMTSurveyDiscoverAlerts.SurveyDownloadError }
        }
        _isLoadingState.update { false }
    }

    private fun validateProgressAlerts(uiState: ZEMTSurveyDiscoverUiState) {
        if (_alertsState.value != null || isFirstAlertEmitted) return
        val alert = when (uiState.userHasProgress) {
            true -> ZEMTSurveyDiscoverAlerts.SavePoint
            false -> ZEMTSurveyDiscoverAlerts.FirstTime
        }
        _alertsState.update { alert }
        isFirstAlertEmitted = true
    }

    private suspend fun validateBreakAlert(uiState: ZEMTSurveyDiscoverUiState) {
        if (uiState.currentGroupQuestionHaveBreak) {
            _alertsState.update { ZEMTSurveyDiscoverAlerts.TakeBreak }
            uiState.nextBreakToShow?.let { breaksRepository.markBreakAsSeen(it.indexGroupQuestion) }
        }
    }

    fun onRestartAlertState() = _alertsState.update { null }

    fun onRetrySurveyDownload() = viewModelScope.launch { downloadSurveyIfNecessary() }

    fun onSaveAnswer(answer: ZEMTDiscoverAnswerUiModel) = viewModelScope.launch {
        when (val result = saveAnswerUseCase.invoke(answer.id)) {
            is ZEMTResult.Success -> Unit
            is ZEMTResult.Error -> {
                handleSaveAnswerError(
                    result.error,
                    isLastGroupQuestions = uiState.value.isLastGroupQuestions
                )
            }
        }
    }

    fun onGroupQuestionsSkip() = viewModelScope.launch {
        uiState.value.currentGroupQuestions?.neutralAnswer?.let {
            when (val result = skipGroupQuestionsUseCase(answerId = it.id)) {
                is ZEMTResult.Success -> Unit
                is ZEMTResult.Error -> {
                    handleSaveAnswerError(result.error, isLastGroupQuestions = true)
                }
            }
        }
    }

    private fun handleSaveAnswerError(
        error: ZEMTSaveAnswerError,
        isLastGroupQuestions: Boolean
    ) {
        when (error) {
            ZEMTSaveAnswerError.MAX_NUMBER_OF_NEUTRAL_ANSWERS_REACHED -> {
                maxNumberOfNeutralAnswersReachedState.update { true }
                _alertsState.update { ZEMTSurveyDiscoverAlerts.MaxNumberOfNeutralAnswers }
            }
        }
        isTimerVisibleState.update { !isLastGroupQuestions }
    }

    fun onScheduleReminder() = surveyReminder.scheduleReminder()

    fun onCancelReminderIfExits() = surveyReminder.cancelPendingReminders()

    fun onSurveyFinished() = viewModelScope.launch {
        _isLoadingState.update { true }
        when (synchronizeAnswersUseCase.invoke()) {
            is ZEMTResult.Error -> {
                _alertsState.update { ZEMTSurveyDiscoverAlerts.SurveyAnswerSynchronizerError }
            }

            is ZEMTResult.Success -> _surveyFinishedState.update { true }
        }
        _isLoadingState.update { false }
    }

    fun onRetrySurveyFinished() = onSurveyFinished()

    fun onFirstLaunch() = viewModelScope.launch { locationUpdater.updateLocation() }

}