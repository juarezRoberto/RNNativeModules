package com.upax.zemytalents.ui.conversations.talentsresume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.domain.models.conversations.ZEMTConversationResult
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ZEMTTalentsResumeViewModel(
    private val getTalents: ZEMTGetUserTalentsUseCase,
    private val getConversationsUseCase: ZEMTGetConversationListUseCase,
    private val getTalentsCompletedByIdUseCase: ZEMTGetTalentsCompletedByIdUseCase,
    getUserDataUseCase: ZEMTGetUserDataUseCase,
    private val getCaptions: ZEMTGetCaptionTextUseCase
) :
    ViewModel() {
    private var _collaboratorName = MutableStateFlow("")
    private var _userProfileUrl = MutableStateFlow("")
    private var _tabList =
        MutableStateFlow<List<ZEMTTalentsResumeFragment.ZEMTTabOptionType>>(emptyList())
    private var _viewType = MutableStateFlow(ZEMTTalentsResumeType.COLLABORATOR_TALENTS)
    private var _selectedTab = MutableStateFlow("")
    private var _showQrCode = MutableStateFlow(false)
    private val _conversationsState =
        MutableStateFlow<ZEMTConversationResult<List<ZEMTConversation>>>(ZEMTConversationResult.Empty)
    private val _isTalentsFinishedState =
        MutableStateFlow<ZEMTConversationResult<Boolean>>(ZEMTConversationResult.Empty)
    private val _talentsSate =
        MutableStateFlow<ZEMTConversationResult<ZEMTTalents>>(ZEMTConversationResult.Empty)

    @Suppress("UNCHECKED_CAST")
    val uiState = combine(
        _talentsSate,
        _collaboratorName,
        _userProfileUrl,
        _tabList,
        _viewType,
        _conversationsState,
        _selectedTab,
        _isTalentsFinishedState,
        _showQrCode,
        getUserDataUseCase.invoke(),
        flow { emit(getUserDataUseCase.getLeaderId()) },
        flow { emit(getCaptions.invoke(ZEMTCaptionCatalog.CONVERSACIONES_TIP)) }
    ) { values ->
        val talentsState = values[0] as ZEMTConversationResult<ZEMTTalents>
        val collaboratorName = values[1] as String
        val userProfileUrl = values[2] as String
        val tabList = values[3] as List<ZEMTTalentsResumeFragment.ZEMTTabOptionType>
        val viewType = values[4] as ZEMTTalentsResumeType
        val conversationsState = values[5] as ZEMTConversationResult<List<ZEMTConversation>>
        val selectedTab = values[6] as String
        val talentsFinishedState = values[7] as ZEMTConversationResult<Boolean>
        val showQrCode = values[8] as Boolean
        val userData = values[9] as ZCSIUser
        val bossId = values[10] as String
        val tipAlertText = values[11] as String

        val filteredTabList = mutableListOf<String>()
        val firstTab = tabList.firstOrNull()
        if (firstTab != null) filteredTabList.add(firstTab.title)
        val talentList =
            if (talentsState is ZEMTConversationResult.Success) talentsState.data else ZEMTTalents(
                emptyList(),
                emptyList()
            )
        val conversationsList =
            if (conversationsState is ZEMTConversationResult.Success) conversationsState.data else emptyList()

        if (talentList.notDominantTalents.isNotEmpty()) filteredTabList.add(
            tabList.find { it == ZEMTTalentsResumeFragment.ZEMTTabOptionType.NO_DOMINANT }?.title.orEmpty()
        )

        if (viewType == ZEMTTalentsResumeType.MY_TALENTS && conversationsList.isNotEmpty()) filteredTabList.add(
            tabList.find { it == ZEMTTalentsResumeFragment.ZEMTTabOptionType.CONVERSATIONS }?.title.orEmpty()
        )
        val isLoading =
            talentsState is ZEMTConversationResult.Loading || conversationsState is ZEMTConversationResult.Loading || talentsFinishedState is ZEMTConversationResult.Loading

        if (selectedTab.isEmpty() && isLoading.not()) _selectedTab.value =
            filteredTabList.firstOrNull().orEmpty()

        val errorType =
            if (conversationsState is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS
            else if (talentsState is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS
            else if (talentsFinishedState is ZEMTConversationResult.Error) ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS_FINISHED
            else null

        val enableQr =
            talentsFinishedState is ZEMTConversationResult.Success && talentsFinishedState.data && viewType == ZEMTTalentsResumeType.MY_TALENTS

        val newBossId = if (viewType == ZEMTTalentsResumeType.MY_TALENTS) bossId else userData.zeusId

        ZEMTTalentsResumeUiModel(
            userName = collaboratorName,
            userProfileUrl = userProfileUrl,
            talentList = talentList,
            tabList = filteredTabList,
            isLoading = isLoading,
            errorType = errorType,
            viewType = viewType,
            conversations = conversationsList,
            selectedTab = selectedTab,
            enableQrCode = enableQr,
            showQrCode = showQrCode,
            userData = userData,
            bossId = newBossId,
            tipAlertText = tipAlertText
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ZEMTTalentsResumeUiModel()
    )

    fun fetchTalents(collaboratorId: String) {
        viewModelScope.launch {
            getTalents(collaboratorId).collect { result ->
                _talentsSate.update { result }
            }
        }
    }

    fun fetchConversations(collaboratorId: String) {
        viewModelScope.launch {
            getConversationsUseCase.invoke(collaboratorId = collaboratorId)
                .collect { conversationResult ->
                    _conversationsState.update { conversationResult }
                }
        }
    }

    fun fetchTalentsFinished(collaboratorId: String) {
        viewModelScope.launch {
            getTalentsCompletedByIdUseCase.invoke(collaboratorId = collaboratorId)
                .collect { result ->
                    _isTalentsFinishedState.update { result }
                }
        }
    }

    fun setLocalData(
        collaboratorName: String,
        userProfileUrl: String,
        tabList: List<ZEMTTalentsResumeFragment.ZEMTTabOptionType>,
        viewType: ZEMTTalentsResumeType
    ) {
        _tabList.value = tabList
        _collaboratorName.value = collaboratorName
        _userProfileUrl.value = userProfileUrl
        _viewType.value = viewType
    }

    fun setSelectedTab(tab: String) {
        _selectedTab.value = tab
    }

    fun showQrCode() {
        _showQrCode.update { true }
    }

    fun hideQrCode() {
        _showQrCode.update { false }
    }
}