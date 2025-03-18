package com.upax.zemytalents.ui.modules.confirm.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.common.orZero
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyConfirmUseCase
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersConfirmUseCase
import com.upax.zemytalents.domain.usecases.modules.confirm.ZEMTSaveConfirmSurveyAnswerUseCase
import com.upax.zemytalents.ui.modules.confirm.survey.mapper.ZEMTTalentsConfirmToUiModelMapper
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmAnswerOptionUiModel
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmServiceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTSurveyConfirmViewModel(
    private val getSurveyConfirmUseCase: ZEMTGetSurveyConfirmUseCase,
    private val talentsConfirmMapper: ZEMTTalentsConfirmToUiModelMapper,
    private val synchronizeAnswersUseCase: ZEMTSynchronizeAnswersConfirmUseCase,
    private val locationUpdater: ZEMTLocationUpdater,
    private val dbSurveyConfirmRepository: ZEMTSurveyConfirmRepository,
    private val saveConfirmSurveyAnswerUseCase: ZEMTSaveConfirmSurveyAnswerUseCase,
) : ViewModel() {

    private val _serviceState: MutableStateFlow<ZEMTSurveyConfirmServiceState> =
        MutableStateFlow(ZEMTSurveyConfirmServiceState.Idle)
    val serviceState = _serviceState.asStateFlow()

    private val _uiState: MutableStateFlow<ZEMTSurveyConfirmUiState> =
        MutableStateFlow(ZEMTSurveyConfirmUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState = _isLoadingState.asStateFlow()

    private val _surveyFinishedState = MutableStateFlow(false)
    val surveyFinishedState = _surveyFinishedState.asStateFlow()

    private val _alertsState = MutableStateFlow<ZEMTSurveyConfirmAlerts?>(null)
    val alertsState = _alertsState.asStateFlow()

    fun getSurvey() = viewModelScope.launch {
        _serviceState.update { ZEMTSurveyConfirmServiceState.Loading }
        when (val result = getSurveyConfirmUseCase.invoke()) {
            is ZEMTResult.Error -> {
                _serviceState.update { ZEMTSurveyConfirmServiceState.Error }
            }

            is ZEMTResult.Success -> {
                dbSurveyConfirmRepository.saveTotalQuestions(result.data)

                var talents = talentsConfirmMapper.invoke(result.data, emptyList(), null)
                var openQuestionId = result.data.first().questions.first().id
                var showSavedProgressDialog = false
                var showTalentIntroduction = true
                // validate if exists progress in surveys
                if (dbSurveyConfirmRepository.hasSavedAnswers()) {
                    val (nextTalentId, nextQuestion) =
                        dbSurveyConfirmRepository.getNextQuestion(result.data)
                    val tempTalents =
                        talentsConfirmMapper.invoke(result.data, emptyList(), nextTalentId)
                    // mark question as complete
                    talents = tempTalents.map { talent ->
                        if (talent.id == nextTalentId) {
                            val updatedQuestions = talent.questions.map {
                                if (it.order < nextQuestion.order) {
                                    it.copy(isCompleted = true)
                                } else it
                            }
                            talent.copy(questions = updatedQuestions)
                        } else talent
                    }
                    openQuestionId = nextQuestion.id
                    showSavedProgressDialog = true
                    showTalentIntroduction = false
                }

                _uiState.update {
                    ZEMTSurveyConfirmUiState(
                        talents = talents,
                        openQuestionId = openQuestionId,
                        showTalentIntroduction = showTalentIntroduction,
                        showSavedProgressDialog = showSavedProgressDialog
                    )
                }
                _serviceState.update { ZEMTSurveyConfirmServiceState.Success }
            }
        }
    }

    private fun toggleOpenQuestion(questionId: Int) {
        _uiState.update {
            it.copy(
                openQuestionId = if (questionId == uiState.value.openQuestionId) null else questionId
            )
        }
    }

    fun saveQuestionAnswer(
        questionId: Int,
        options: List<ZEMTSurveyConfirmAnswerOptionUiModel>
    ) = viewModelScope.launch {
        // collapsa la pregunta
        toggleOpenQuestion(questionId)
        // actualiza las respuestas de la pregunta y la marca como completada
        val updatedTalents = uiState.value.talents.map { talent ->
            val newQuestions = talent.questions.map { question ->
                if (question.id == questionId) {
                    options.filter { it.isChecked }.forEach { answer ->
                        saveConfirmSurveyAnswerUseCase.invoke(talent, question, answer)
                    }
                    question.copy(isCompleted = true, answerOptions = options)
                } else question
            }
            talent.copy(
                questions = newQuestions,
                finished = newQuestions.all { it.isCompleted }
            )
        }
        // obtiene el talento que se esta contestando
        val updatedTalent = updatedTalents.find { it.id == _uiState.value.currentTalent?.id }

        // Se valida si ya estamos en el ultimo talento, y si todas las preguntas de este ya fueron respondidas
        if (updatedTalent?.order == updatedTalents.size - 1 && updatedTalent.allQuestionsAnswered) {
            _uiState.update { it.copy(talents = updatedTalents, showFinishedSurveyDialog = true) }
        } else {
            // se valida si todas las preguntas del talento ya fueron respondidas
            if (updatedTalent?.allQuestionsAnswered == true) {
                val remaining = updatedTalents.size - (updatedTalent.order + 1)
                // mostrar el dialogo de preguntas restantes
                _uiState.update {
                    it.copy(
                        talents = updatedTalents,
                        showRemainingQuestionsDialog =
                        ZEMTSurveyConfirmUiState.RemainingTalents(remaining)
                    )
                }

            } else {
                // busca la pregunta actual y despues para la pregunta siguiente, busca el item que tenga el order + 1
                updatedTalent?.questions?.find { it.id == questionId }?.also { currentQuestion ->
                    val nextQuestion = updatedTalent.questions
                        .find { it.order == (currentQuestion.order + 1) }

                    //  actualiza la pregunta, las respuestas y la pregunta siguiente
                    _uiState.update {
                        it.copy(
                            talents = updatedTalents,
                            openQuestionId = nextQuestion?.id
                        )
                    }
                }
            }
        }
    }

    fun moveToNextTalent() {
        val currentTalent = uiState.value.talents.find { it.selected }
        val nextTalent = uiState.value.talents[currentTalent?.order.orZero() + 1]
        val talents = uiState.value.talents.map { it.copy(selected = it.id == nextTalent.id) }
        _uiState.update {
            it.copy(
                talents = talents,
                openQuestionId = nextTalent.questions.first().id,
                showRemainingQuestionsDialog = null,
                showTalentIntroduction = true
            )
        }
    }

    // elimina las respuestas de la preguntas del talent actual,
    fun clearCurrentTalentAnswers() = viewModelScope.launch {
        val currentTalent = uiState.value.currentTalent
        val updatedTalents = uiState.value.talents.map { talent ->
            if (talent.id == currentTalent?.id) {
                val updatedQuestions = talent.questions.map { question ->
                    val updatedAnswers = question.answerOptions.map { answer ->
                        answer.copy(isChecked = false)
                    }
                    question.copy(isCompleted = false, answerOptions = updatedAnswers)
                }
                talent.copy(questions = updatedQuestions, finished = false)
            } else {
                talent
            }
        }
        _uiState.update {
            it.copy(
                talents = updatedTalents,
                openQuestionId = currentTalent?.questions?.first()?.id.orZero(),
                showRemainingQuestionsDialog = null,
                showFinishedSurveyDialog = false
            )
        }
        dbSurveyConfirmRepository.deleteAnswersByTalentId(currentTalent!!.id)
    }

    fun hideIntroduction() {
        _uiState.update { it.copy(showTalentIntroduction = false) }
    }

    fun onSurveyFinished() = viewModelScope.launch {
        _isLoadingState.update { true }
        when (synchronizeAnswersUseCase.invoke()) {
            is ZEMTResult.Error -> {
                _alertsState.update { ZEMTSurveyConfirmAlerts.SURVEY_ANSWER_SYNCHRONIZER_ERROR }
            }

            is ZEMTResult.Success -> {
                dbSurveyConfirmRepository.deleteAllSavedAnswers()
                _surveyFinishedState.update { true }
            }
        }
        _isLoadingState.update { false }
    }

    fun onRetrySurveyFinished() = onSurveyFinished()

    fun onRestartAlertState() = _alertsState.update { null }

    fun onFirstLaunch() = viewModelScope.launch { locationUpdater.updateLocation() }

    fun resetShowSavedProgressDialogState() {
        _uiState.update { it.copy(showSavedProgressDialog = false) }
    }

}