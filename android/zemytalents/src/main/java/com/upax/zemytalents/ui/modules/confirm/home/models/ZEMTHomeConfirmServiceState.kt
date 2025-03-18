package com.upax.zemytalents.ui.modules.confirm.home.models

internal sealed interface ZEMTHomeConfirmServiceState {
    data object Idle : ZEMTHomeConfirmServiceState
    data object Loading : ZEMTHomeConfirmServiceState
    data object Error : ZEMTHomeConfirmServiceState
    data class Success(val uiState: ZEMTHomeConfirmUiState) : ZEMTHomeConfirmServiceState
}