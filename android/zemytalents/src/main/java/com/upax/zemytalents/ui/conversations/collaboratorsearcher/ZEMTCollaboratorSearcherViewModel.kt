package com.upax.zemytalents.ui.conversations.collaboratorsearcher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.repositories.ZEMTCollaboratorsRepository
import com.upax.zemytalents.domain.usecases.conversations.ZEMTConversationProgressUseCase
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTMakeConversationUiData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
internal class ZEMTCollaboratorSearcherViewModel(
    private val collaboratorsRepository: ZEMTCollaboratorsRepository,
    private val progressUseCase: ZEMTConversationProgressUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewType: ZEMTCollaboratorListViewType = savedStateHandle
        .get<ZEMTCollaboratorListViewType>("viewType")!!

    private val searchQuery = MutableStateFlow("")

    private var _makeConversationState = MutableStateFlow(ZEMTMakeConversationUiData())
    val makeConversationState = _makeConversationState.asStateFlow()

    var collaboratorSelected: ZEMTCollaboratorInCharge? = null
        private set

    val collaborators: StateFlow<List<ZEMTCollaboratorInCharge>?> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            flow {
                if (query.isEmpty()) {
                    emit(null)
                } else {
                    emit(collaboratorsRepository.findCollaboratorsByName(query))
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _collaboratorAction = MutableStateFlow<ZEMTCollaboratorSearcherActionsState?>(null)
    val collaboratorAction = _collaboratorAction.asStateFlow()

    fun onCollaboratorSelected(collaborator: ZEMTCollaboratorInCharge) {
        collaboratorSelected = collaborator
        if (!collaborator.talentsCompleted) {
            _collaboratorAction.update { ZEMTCollaboratorSearcherActionsState.TalentsNoCompleted }
            return
        }
        val action = when (viewType) {
            ZEMTCollaboratorListViewType.MAKE_CONVERSATION -> ZEMTCollaboratorSearcherActionsState
                .NavigateToMakeConversation

            ZEMTCollaboratorListViewType.TALENTS -> ZEMTCollaboratorSearcherActionsState
                .NavigateToDetail
        }
        _collaboratorAction.update { action }
    }

    fun restCollaboratorAction() {
        _collaboratorAction.update { null }
    }

    fun resetCollaboratorSelected() {
        collaboratorSelected = null
    }

    fun onSearchTextChange(query: String) {
        searchQuery.update { query }
    }

    fun onStartConversation(collaboratorId: String, collaboratorName: String) {
        viewModelScope.launch {
            val localProgress = progressUseCase.invoke(collaboratorId = collaboratorId)
            var basicData = _makeConversationState.value.copy(
                collaboratorName = collaboratorName,
                collaboratorId = collaboratorId
            )
            basicData = if (localProgress != null) {
                basicData.copy(showResetDialog = true)
            } else basicData.copy(navigateToMakeConversation = true)
            _makeConversationState.update { basicData }
        }
    }

    fun setResetProgress(resetProgress: Boolean) {
        _makeConversationState.update { it.copy(resetProgress = resetProgress) }
    }

    fun resetCollaboratorProgress(collaboratorId: String) {
        viewModelScope.launch {
            progressUseCase.deleteProgress(collaboratorId = collaboratorId)
            navigateToMakeConversation()
        }
    }

    fun resetMakeConversationState() {
        _makeConversationState.update { ZEMTMakeConversationUiData() }
    }

    fun navigateToMakeConversation() {
        _makeConversationState.update { it.copy(navigateToMakeConversation = true) }
    }


}