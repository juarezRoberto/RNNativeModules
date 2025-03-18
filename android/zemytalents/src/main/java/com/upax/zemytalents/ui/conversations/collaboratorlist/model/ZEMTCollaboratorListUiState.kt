package com.upax.zemytalents.ui.conversations.collaboratorlist.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge

internal data class ZEMTCollaboratorListUiState(
    val isLoading: Boolean = false,
    val collaboratorList: List<ZEMTCollaboratorInCharge> = emptyList(),
    val showCarrousel: Boolean = false,
    val makeConversationData: ZEMTMakeConversationUiData = ZEMTMakeConversationUiData(),
    val isError: Boolean = false,
    val captions: ZEMTCollaboratorListCaptions = ZEMTCollaboratorListCaptions(),
)

internal data class ZEMTMakeConversationUiData(
    val collaboratorId: String = String.EMPTY,
    val collaboratorName: String = String.EMPTY,
    val resetProgress: Boolean = false,
    val navigateToMakeConversation: Boolean = false,
    val showResetDialog: Boolean = false,
)

internal data class ZEMTCollaboratorListCaptions(
    val collaboratorsCaption: String = String.EMPTY,
    val selectCollaboratorCaption: String = String.EMPTY,
    val makeConversationCaption: String = String.EMPTY,
    val carrouselSlide2: String = String.EMPTY,
    val carrouselSlide4: String = String.EMPTY,
)