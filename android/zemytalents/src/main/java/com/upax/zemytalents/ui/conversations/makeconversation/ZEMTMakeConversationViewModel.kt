package com.upax.zemytalents.ui.conversations.makeconversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.models.conversations.ZEMTQrData
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTConversationProgressUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetPhrasesUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTSaveConversationUseCase
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTConversationData
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationCaptions
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationUiState
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTPhraseData
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTMakeConversationViewModel(
    getConversationUseCase: ZEMTGetConversationListUseCase,
    private val getPhrasesUseCase: ZEMTGetPhrasesUseCase,
    private val progressUseCase: ZEMTConversationProgressUseCase,
    private val saveConversationUseCase: ZEMTSaveConversationUseCase,
    private val getCaptions: ZEMTGetCaptionTextUseCase
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
        }
    }

    private var _makeConversationData = MutableStateFlow(ZEMTMakeConversationProgress())
    private var _phrasesLoadingState = MutableStateFlow(false)
    private var _errorState = MutableStateFlow(false)
    private var _saveConversationErrorState = MutableStateFlow(false)
    private var _phrasesList = MutableStateFlow(emptyList<ZEMTCheckGroupItem>())
    private var _saveConversationLoadingState = MutableStateFlow(false)
    private var _saveConversationSuccessState = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val collaboratorFlow: Flow<ZEMTConversationResult<List<ZEMTConversation>>> =
        retryTrigger
            .flatMapLatest {
                getConversationUseCase.invoke()
            }

    private fun getPhrases(conversationId: String, collaboratorId: String) {
        viewModelScope.launch {
            getPhrasesUseCase.invoke(
                conversationId = conversationId,
                collaboratorId = collaboratorId
            ).collect { result ->
                _phrasesLoadingState.value = result is ZEMTConversationResult.Loading
                _errorState.update { result is ZEMTConversationResult.Error }
                when (result) {
                    is ZEMTConversationResult.Success -> {
                        _phrasesList.update { result.data.map { it.toCheckGroupItem() } }
                    }

                    else -> Unit
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    val uiState =
        combine(
            _makeConversationData,
            collaboratorFlow,
            _phrasesLoadingState,
            _errorState,
            _phrasesList,
            _saveConversationLoadingState,
            _saveConversationErrorState,
            _saveConversationSuccessState
        ) { results ->
            val makeConversationData = results[0] as ZEMTMakeConversationProgress
            val conversationResult = results[1] as ZEMTConversationResult<List<ZEMTConversation>>
            val phrasesLoadingState = results[2] as Boolean
            val errorState = results[3] as Boolean
            val phrasesList = results[4] as List<ZEMTCheckGroupItem>
            val saveConversationLoadingState = results[5] as Boolean
            val saveConversationErrorState = results[6] as Boolean
            val saveConversationSuccessState = results[7] as Boolean

            val conversationList =
                if (conversationResult is ZEMTConversationResult.Success) conversationResult.data else emptyList()

            val captions = ZEMTMakeConversationCaptions(
                step2Caption = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_CONVERSACION_PASO2),
                step4Caption = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_CONVERSACION_PASO4)
            )
            ZEMTMakeConversationUiState(
                conversationList = conversationList.map { it.toCheckGroupItem() },
                phraseList = phrasesList,
                conversationData = makeConversationData,
                isLoading = phrasesLoadingState || conversationResult is ZEMTConversationResult.Loading || saveConversationLoadingState,
                isError = errorState || conversationResult is ZEMTConversationResult.Error,
                isSaveConversationError = saveConversationErrorState,
                isSaveConversationSuccess = saveConversationSuccessState,
                captions = captions
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ZEMTMakeConversationUiState()
        )

    fun setSelectedConversation(data: ZEMTConversationData) {
        _makeConversationData.update { it.copy(conversationId = data.id) }
    }

    fun setSelectedPhrase(data: ZEMTPhraseData) {
        _makeConversationData.update { it.copy(phraseId = data.id) }
    }

    fun setConversationRealized(data: Boolean) {
        _makeConversationData.update { it.copy(isConversationMade = data) }
    }

    fun setComment(data: String) {
        _makeConversationData.update { it.copy(comment = data) }
    }

    fun previousStep() {
        _makeConversationData.update { it.copy(currentStep = it.currentStep.getPrevStep()) }
    }

    fun nextStep(collaboratorId: String) {
        viewModelScope.launch {
            val conversationData = _makeConversationData.value.copy(collaboratorId = collaboratorId)
            progressUseCase.updateProgress(conversationData)
        }
        _makeConversationData.update { it.copy(currentStep = it.currentStep.getNextStep()) }
        val currentStep = _makeConversationData.value.currentStep
        if (currentStep.isChoosePhrase() || currentStep.isShowPhrase() || currentStep.isSummary()) {
            getPhrases(
                conversationId = _makeConversationData.value.conversationId,
                collaboratorId = collaboratorId
            )
        }
    }

    fun onStart(collaboratorId: String) {
        viewModelScope.launch {
            val localData = progressUseCase.invoke(collaboratorId = collaboratorId)
            if (localData != null) {
                _makeConversationData.update { localData }
                val currentProgress = localData.currentStep
                if (currentProgress.isChoosePhrase() || currentProgress.isShowPhrase() || currentProgress.isSummary()) {
                    getPhrases(
                        conversationId = localData.conversationId,
                        collaboratorId = collaboratorId
                    )
                }
            }
        }
    }

    fun saveConversation() {
        viewModelScope.launch {
            saveConversationUseCase.invoke(_makeConversationData.value).collect { result ->
                _saveConversationLoadingState.update { result is ZEMTConversationResult.Loading }
                _saveConversationErrorState.update { result is ZEMTConversationResult.Error }
                when (result) {
                    is ZEMTConversationResult.Success -> _saveConversationSuccessState.update { true }
                    else -> Unit
                }
            }
        }
    }

    fun resetSaveConversationState() {
        viewModelScope.launch {
            _saveConversationSuccessState.update { false }
        }
    }

    fun retryServiceCall(collaboratorId: String) {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
            if (_makeConversationData.value.conversationId.isNotEmpty())
                getPhrases(
                    conversationId = _makeConversationData.value.conversationId,
                    collaboratorId = collaboratorId
                )
        }
    }
}