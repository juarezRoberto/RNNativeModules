package com.upax.zemytalents.ui.conversations.conversationhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationsHistoryUseCase
import com.upax.zemytalents.ui.conversations.conversationhistory.model.ZEMTConversationHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTConversationViewModel(
    private val getConversationsHistory: ZEMTGetConversationsHistoryUseCase,
    private val getLocalTexts: ZEMTGetCaptionTextUseCase
) : ViewModel() {
    private val _conversationsState = MutableStateFlow(ZEMTConversationHistoryUiState())
    val uiState
        get() = _conversationsState.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ZEMTConversationHistoryUiState()
        )

    fun getConversationsHistory(collaboratorId: String, bossId: String, conversationId: String) {
        viewModelScope.launch {
            getConversationsHistory.invoke(collaboratorId, bossId, conversationId)
                .collect { result ->
                    _conversationsState.update {
                        _conversationsState.value.copy(
                            isLoading = result is ZEMTConversationResult.Loading,
                            isError = result is ZEMTConversationResult.Error,
                            conversations = if (result is ZEMTConversationResult.Success) result.data else emptyList(),
                            tipAlertText = getLocalTexts.invoke(ZEMTCaptionCatalog.MIS_TALENTOS_TAB_HISTORIAL_TIP1)
                        )
                    }
                }
        }
    }

    fun showTipsAlert() {
        _conversationsState.update { it.copy(showTipsAlert = true) }
    }

    fun hideTipsAlert() {
        _conversationsState.update { it.copy(showTipsAlert = false) }
    }
}