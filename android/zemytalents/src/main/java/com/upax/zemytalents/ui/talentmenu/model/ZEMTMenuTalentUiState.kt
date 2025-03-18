package com.upax.zemytalents.ui.talentmenu.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType

internal data class ZEMTMenuTalentUiState(
    val isLoading: Boolean = true,
    val errorType: ZEMTConversationsErrorType? = null,
    val canMakeConversation: Boolean = false,
    val user: ZCSIUser = ZCSIUser(),
    val menuOptionList: List<ZEMTMenuOption> = emptyList(),
    val collaboratorsInCharge: List<ZEMTCollaboratorInCharge> = emptyList(),
    val showQrCode: Boolean = false,
    val redirectToTalents: Boolean = false,
    val isTalentsCompleted: Boolean = false,
    val homeCaption: ZEMTTalentResumeCaptionsUiState = ZEMTTalentResumeCaptionsUiState(),
)

internal data class ZEMTTalentResumeCaptionsUiState(
    val homeCaption: String = String.EMPTY,
    val optionCollaboratorCaption: String = String.EMPTY,
)