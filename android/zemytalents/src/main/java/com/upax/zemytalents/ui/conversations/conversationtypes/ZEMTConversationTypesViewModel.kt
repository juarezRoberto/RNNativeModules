package com.upax.zemytalents.ui.conversations.conversationtypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.ui.conversations.conversationtypes.model.ZEMTConversationTypesUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class ZEMTConversationTypesViewModel(
    getConversationList: ZEMTGetConversationListUseCase,
    private val getCaptions: ZEMTGetCaptionTextUseCase
) : ViewModel() {
    val uiState =
        combine(
            getConversationList.invoke()
        ) { flowArray ->
            val conversationResult =
                flowArray.firstOrNull() as ZEMTConversationResult<List<ZEMTConversation>>
            val conversationList =
                if (conversationResult is ZEMTConversationResult.Success) conversationResult.data else emptyList()
            val loadingState = conversationResult is ZEMTConversationResult.Loading
            val errorState = conversationResult is ZEMTConversationResult.Error
            ZEMTConversationTypesUiState(
                isLoading = loadingState,
                conversationList = conversationList,
                isError = errorState,
                tipMessage = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_TIPOS_CONVERSACION_TIP_SLIDE1)
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ZEMTConversationTypesUiState()
        )
}