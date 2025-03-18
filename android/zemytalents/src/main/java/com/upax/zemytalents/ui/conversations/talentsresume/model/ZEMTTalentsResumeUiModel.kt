package com.upax.zemytalents.ui.conversations.talentsresume.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation

internal data class ZEMTTalentsResumeUiModel(
    val userName: String = "",
    val userProfileUrl: String = "",
    val talentList: ZEMTTalents = ZEMTTalents(emptyList(), emptyList()),
    val tabList: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorType: ZEMTConversationsErrorType? = null,
    val viewType: ZEMTTalentsResumeType = ZEMTTalentsResumeType.COLLABORATOR_TALENTS,
    val conversations: List<ZEMTConversation> = emptyList(),
    val selectedTab: String = tabList.firstOrNull().orEmpty(),
    val showQrCode: Boolean = false,
    val enableQrCode: Boolean = false,
    val userData: ZCSIUser = ZCSIUser(),
    val bossId: String = String.EMPTY,
    val tipAlertText: String = String.EMPTY
)
