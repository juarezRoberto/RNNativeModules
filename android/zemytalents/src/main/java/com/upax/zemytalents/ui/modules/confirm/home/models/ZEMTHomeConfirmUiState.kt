package com.upax.zemytalents.ui.modules.confirm.home.models

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.models.ZEMTTalents

internal data class ZEMTHomeConfirmUiState(
    val user: ZCSIUser? = null,
    val talents: ZEMTTalents? = null
)
