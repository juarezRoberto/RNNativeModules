package com.upax.zemytalents.ui.mytalents

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.domain.models.ZEMTTalents

internal class ZEMTMyTalentsUiState(
    val user: ZCSIUser? = null,
    val talents: ZEMTTalents? = null
)