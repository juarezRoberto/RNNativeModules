package com.upax.zemytalents.ui.talentmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTCaptionsDownloader
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsMenuUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetCollaboratorsInChargeUseCase
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuTalentUiState
import com.upax.zemytalents.ui.talentmenu.model.ZEMTTalentResumeCaptionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTMenuTalentViewModel(
    getUser: ZEMTGetUserDataUseCase,
    private val getCollaboratorsInCharge: ZEMTGetCollaboratorsInChargeUseCase,
    private val captionsDownloader: ZEMTCaptionsDownloader,
    private val getTalentsMenu: ZEMTGetTalentsMenuUseCase,
    private val localPreferences: ZEMTLocalPreferences,
    private val getTalentsCompletedById: ZEMTGetTalentsCompletedByIdUseCase,
    private val getCaptionTextUseCase: ZEMTGetCaptionTextUseCase
) : ViewModel() {
    private var _showQrCode = MutableStateFlow(false)
    private val _collaboratorsInCharge =
        MutableStateFlow<ZEMTConversationResult<List<ZEMTCollaboratorInCharge>>>(
            ZEMTConversationResult.Empty
        )
    private val _getTalentsCompletedById =
        MutableStateFlow<ZEMTConversationResult<Boolean>>(ZEMTConversationResult.Empty)
    private val _getServiceText =
        MutableStateFlow<ZEMTConversationResult<Unit>>(ZEMTConversationResult.Empty)
    val uiState =
        combine(
            getUser.invoke(),
            _collaboratorsInCharge,
            _showQrCode,
            _getTalentsCompletedById,
            _getServiceText
        ) { user, collaboratorResult, showQrCode, talentsCompletedResult, getServiceText ->
            val collaborators =
                if (collaboratorResult is ZEMTConversationResult.Success) collaboratorResult.data else emptyList()
            val isLoading =
                collaboratorResult is ZEMTConversationResult.Loading || talentsCompletedResult is ZEMTConversationResult.Loading || getServiceText is ZEMTConversationResult.Loading
            val isError =
                if (collaboratorResult is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_COLLABORATORS_IN_CHARGE
                else if (talentsCompletedResult is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS_COMPLETED
                else if (getServiceText is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_SERVICE_TEXT
                else null

            val homeCaption =
                if (getServiceText is ZEMTConversationResult.Success) ZEMTTalentResumeCaptionsUiState(
                    homeCaption = getCaptionTextUseCase.invoke(ZEMTCaptionCatalog.FORMADOR_HOME_BIENVENIDA),
                    optionCollaboratorCaption = getCaptionTextUseCase.invoke(ZEMTCaptionCatalog.FORMADOR_OPCION_CONVERSACIONES)
                ) else ZEMTTalentResumeCaptionsUiState()

            ZEMTMenuTalentUiState(
                user = user,
                collaboratorsInCharge = collaborators,
                canMakeConversation = collaborators.isNotEmpty(),
                isLoading = isLoading,
                errorType = isError,
                menuOptionList = getTalentsMenu(
                    collaborators.isNotEmpty(),
                    collaboratorText = homeCaption.optionCollaboratorCaption
                ),
                showQrCode = showQrCode,
                redirectToTalents = !isLoading && collaborators.isEmpty() && isError == null,
                isTalentsCompleted = talentsCompletedResult is ZEMTConversationResult.Success && talentsCompletedResult.data,
                homeCaption = homeCaption
            )
        }.onStart {
            captionsDownloader.download()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ZEMTMenuTalentUiState())

    fun showQrCode() {
        _showQrCode.update { true }
    }

    fun hideQrCode() {
        _showQrCode.update { false }
    }

    fun skipOnboarding() = localPreferences.makeConversationOnboardingShown

    fun fetchServiceText() {
        viewModelScope.launch {
            captionsDownloader.download().collect { result ->
                _getServiceText.update { result }
            }
        }
    }

    fun fetchCollaboratorsInCharge() {
        viewModelScope.launch {
            getCollaboratorsInCharge.invoke().collect { result ->
                _collaboratorsInCharge.update { result }
            }
        }
    }

    fun fetchTalentsCompletedById() {
        viewModelScope.launch {
            getTalentsCompletedById.invoke().collect { result ->
                _getTalentsCompletedById.update { result }
            }
        }
    }

}