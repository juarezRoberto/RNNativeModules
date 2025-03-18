package com.upax.zemytalents.ui.modules.apply.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.combineResults
import com.upax.zemytalents.domain.usecases.ZEMTGetMyUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyUseCase
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSaveSurveyApplyAnswerUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeApplyAnswersUseCase
import com.upax.zemytalents.ui.modules.apply.survey.mapper.ZEMTSurveyApplyAnswerStatusMapper
import com.upax.zemytalents.ui.modules.apply.survey.mapper.ZEMTTalentsApplyToUiModelMapper
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.apply.survey.model.ZEMTSurveyApplyAnswerOptionUiModel.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTSurveyApplyViewModel(
    private val getSurveyApplyUseCase: ZEMTGetSurveyApplyUseCase,
    private val getUserTalentsUseCase: ZEMTGetMyUserTalentsUseCase,
    private val surveyTalentsToUiModelMapper: ZEMTTalentsApplyToUiModelMapper,
    private val synchronizeAnswersUseCase: ZEMTSynchronizeApplyAnswersUseCase,
    private val saveSurveyApplyUseCase: ZEMTSaveSurveyApplyAnswerUseCase,
    private val locationUpdater: ZEMTLocationUpdater
) : ViewModel() {

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState = _isLoadingState.asStateFlow()

    private val _uiState = MutableStateFlow(ZEMTSurveyApplyUiState())
    val uiState = _uiState.asStateFlow()

    private val _surveyFinishedState = MutableStateFlow(false)
    val surveyFinishedState = _surveyFinishedState.asStateFlow()

    private val _alertsState = MutableStateFlow<ZEMTSurveyApplyAlerts?>(null)
    val alertsState = _alertsState.asStateFlow()

    fun onStart() = viewModelScope.launch {
        _isLoadingState.update { true }
        val surveyTalents = async { getSurveyApplyUseCase.invoke() }
        val userTalents = async { getUserTalentsUseCase.invoke() }

        when (val result = combineResults(surveyTalents.await(), userTalents.await())) {
            is ZEMTResult.Error -> {
                _alertsState.update { ZEMTSurveyApplyAlerts.SurveyDownloadedError }
            }

            is ZEMTResult.Success -> {
                val surveyTalentsUi = surveyTalentsToUiModelMapper.invoke(
                    surveyTalents = result.data.first,
                    userTalents = result.data.second.dominantTalents
                )
                _uiState.update {
                    it.copy(
                        dominantTalents = result.data.second.dominantTalents,
                        surveyTalents = surveyTalentsUi
                    )
                }
                validateSurveyProgress()
            }
        }
        _isLoadingState.update { false }
    }

    private fun validateSurveyProgress() {
        when {
            _uiState.value.surveyFinished -> {
                _alertsState.update { ZEMTSurveyApplyAlerts.SurveyFinished }
            }
            _uiState.value.isInProgress -> {
                _alertsState.update { ZEMTSurveyApplyAlerts.SurveyInProgress }
            }
        }
    }

    fun onAnswerStatusChange(
        answer: ZEMTSurveyApplyAnswerOptionUiModel, status: Status
    ) = viewModelScope.launch {
        val currentTalent = _uiState.value.currentTalent ?: return@launch
        saveSurveyApplyUseCase.invoke(
            answerId = answer.id,
            questionId = answer.questionId,
            status = ZEMTSurveyApplyAnswerStatusMapper.toDomainModel(status)
        )
        val newQuestion = currentTalent.question.updateAnswer(answer.copy(status = status))
        val newTalent = currentTalent.copy(question = newQuestion)
        _uiState.update { it.updateTalent(newTalent) }

        if (newTalent.allAnswersQuestionChecked) {
            _alertsState.update {
                ZEMTSurveyApplyAlerts.TalentApplied(
                    talent = uiState.value.currentTalent?.name.orEmpty(),
                    remainingTalents = uiState.value.totalTalentsPendingToAnswer
                )
            }
        }
    }

    fun onNextTalent() {
        val currentTalent = _uiState.value.currentTalent ?: return
        _uiState.update { it.updateTalent(currentTalent.finishTalent()) }

        if (uiState.value.surveyFinished) {
            _alertsState.update { ZEMTSurveyApplyAlerts.SurveyFinished }
        } else {
            val nextTalent = uiState.value.nextTalent!!
            _uiState.update { it.updateTalent(nextTalent.copy(selected = true)) }
        }
    }

    fun onSurveyFinished() = viewModelScope.launch {
        _isLoadingState.update { true }
        when (synchronizeAnswersUseCase.invoke()) {
            is ZEMTResult.Error -> {
                _alertsState.update { ZEMTSurveyApplyAlerts.SurveyAnswerSyncError }
            }

            is ZEMTResult.Success -> _surveyFinishedState.update { true }
        }
        _isLoadingState.update { false }
    }

    fun resetAlertsState() {
        _alertsState.update { null }
    }

    fun onFirstLaunch() = viewModelScope.launch { locationUpdater.updateLocation() }

}