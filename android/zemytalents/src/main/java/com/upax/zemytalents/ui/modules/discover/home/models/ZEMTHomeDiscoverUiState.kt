package com.upax.zemytalents.ui.modules.discover.home.models

import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel

internal data class ZEMTHomeDiscoverUiState(
    val user: ZCSIUser? = null,
    val progress: Float = 0.01f,
    val modules: List<ZEMTModuleUiModel> = emptyList()
)