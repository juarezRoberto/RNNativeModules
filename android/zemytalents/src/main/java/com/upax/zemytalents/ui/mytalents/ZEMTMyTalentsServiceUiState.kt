package com.upax.zemytalents.ui.mytalents

internal sealed interface ZEMTMyTalentsServiceUiState {
    data object Idle : ZEMTMyTalentsServiceUiState
    data object Loading : ZEMTMyTalentsServiceUiState
    data object Error : ZEMTMyTalentsServiceUiState
    data class Success(val uiState: ZEMTMyTalentsUiState) : ZEMTMyTalentsServiceUiState
}
