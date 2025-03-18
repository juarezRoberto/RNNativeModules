package com.upax.zemytalents.ui.conversations.collaboratordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorDetailUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

internal class ZEMTCollaboratorDetailViewModel(
    getConversationList: ZEMTGetConversationListUseCase,
    getUserDataUseCase: ZEMTGetUserDataUseCase
) :
    ViewModel() {
    val uiState = combine(
        getConversationList.invoke(),
        flow { emit(getUserDataUseCase.getLeaderId()) }) { conversationResult, leaderId ->
        val conversationList =
            if (conversationResult is ZEMTConversationResult.Success) conversationResult.data else emptyList()
        ZEMTCollaboratorDetailUiState(
            conversationList = conversationList,
            isLoading = conversationResult is ZEMTConversationResult.Loading,
            isError = conversationResult is ZEMTConversationResult.Error,
            leaderId = leaderId
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ZEMTCollaboratorDetailUiState())
}