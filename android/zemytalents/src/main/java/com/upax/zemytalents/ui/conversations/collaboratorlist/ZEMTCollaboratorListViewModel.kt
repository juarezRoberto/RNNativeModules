package com.upax.zemytalents.ui.conversations.collaboratorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTConversationProgressUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListCaptions
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListUiState
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTMakeConversationUiData
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

internal class ZEMTCollaboratorListViewModel(
    collaboratorUseCase: ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase,
    private val progressUseCase: ZEMTConversationProgressUseCase,
    private val getCaptions: ZEMTGetCaptionTextUseCase
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
        }
    }

    private var _showCarrousel = MutableStateFlow(false)
    private var _makeConversationData = MutableStateFlow(ZEMTMakeConversationUiData())
    private var _isLoading = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val collaboratorFlow: Flow<ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>> =
        retryTrigger
            .flatMapLatest {
                collaboratorUseCase.invoke()
            }


    val uiState = combine(
        collaboratorFlow,
        _showCarrousel,
        _makeConversationData,
        _isLoading
    ) { collaboratorResult, showCarrousel, makeConversationData, isLoading2 ->
        val isLoading = collaboratorResult is ZEMTConversationResult.Loading || isLoading2
        val collaboratorList =
            if (collaboratorResult is ZEMTConversationResult.Success) collaboratorResult.data else emptyList()

        val captions = ZEMTCollaboratorListCaptions(
            collaboratorsCaption = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_OPCION_CONVERSACIONES),
            selectCollaboratorCaption = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_OPCION_COLABORADORES),
            makeConversationCaption = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_CONVERSACION_SELECCION),
            carrouselSlide2 = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_TIP_SLIDE2),
            carrouselSlide4 = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_TIP_SLIDE4)
        )

        ZEMTCollaboratorListUiState(
            collaboratorList = collaboratorList,
            showCarrousel = showCarrousel,
            isLoading = isLoading,
            makeConversationData = makeConversationData,
            isError = collaboratorResult is ZEMTConversationResult.Error,
            captions = captions
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ZEMTCollaboratorListUiState())

    fun setShowCarrousel(show: Boolean) {
        viewModelScope.launch {
            _showCarrousel.value = show
        }
    }

    fun onStartConversation(collaboratorId: String, collaboratorName: String) {
        viewModelScope.launch {
            val localProgress = progressUseCase.invoke(collaboratorId = collaboratorId)
            var basicData = _makeConversationData.value.copy(
                collaboratorName = collaboratorName,
                collaboratorId = collaboratorId
            )
            basicData = if (localProgress != null) {
                basicData.copy(showResetDialog = true)
            } else basicData.copy(navigateToMakeConversation = true)
            _makeConversationData.update { basicData }
        }
    }

    fun setResetProgress(resetProgress: Boolean) {
        viewModelScope.launch {
            _makeConversationData.update { it.copy(resetProgress = resetProgress) }
        }
    }

    fun resetCollaboratorProgress(collaboratorId: String) {
        viewModelScope.launch {
            progressUseCase.deleteProgress(collaboratorId = collaboratorId)
            navigateToMakeConversation()
        }
    }

    fun resetMakeConversationState() {
        viewModelScope.launch {
            _makeConversationData.update { ZEMTMakeConversationUiData() }
        }
    }

    fun navigateToMakeConversation() {
        viewModelScope.launch {
            _makeConversationData.update { it.copy(navigateToMakeConversation = true) }
        }
    }

    fun retry() {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
        }
    }
}