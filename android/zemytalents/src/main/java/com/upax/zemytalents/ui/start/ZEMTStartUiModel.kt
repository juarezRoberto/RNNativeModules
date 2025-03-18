package com.upax.zemytalents.ui.start

import com.upax.zemytalents.domain.models.modules.ZEMTModule

internal sealed interface ZEMTStartUiModel {
    data object Idle : ZEMTStartUiModel
    data object Loading : ZEMTStartUiModel
    data object Error : ZEMTStartUiModel
    data object ErrorCaptions : ZEMTStartUiModel
    data object SuccessCaptions: ZEMTStartUiModel
    data class Success(
        val modules: List<ZEMTModule>,
        val wasIntroductionViewed: Boolean
    ) : ZEMTStartUiModel
}