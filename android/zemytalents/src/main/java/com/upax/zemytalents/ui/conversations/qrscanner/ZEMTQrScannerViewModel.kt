package com.upax.zemytalents.ui.conversations.qrscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upax.zemytalents.domain.models.conversations.ZEMTQrData
import com.upax.zemytalents.ui.conversations.qrscanner.model.ZEMTQRScannerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class ZEMTQrScannerViewModel : ViewModel() {
    private var _qrNotValid = MutableStateFlow(false)
    private var _qrSuccess = MutableStateFlow(false)
    private var _qrError = MutableStateFlow(false)
    var qrData: ZEMTQrData? = null
        private set

    val uiState = combine(_qrNotValid, _qrSuccess, _qrError) { showNotValidQr, qrSuccess, qrError ->
        ZEMTQRScannerUiState(qrNotValid = showNotValidQr, qrSuccess = qrSuccess, qrError = qrError)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ZEMTQRScannerUiState())

    fun setShowNotValidQr(show: Boolean) {
        _qrNotValid.update { show }
    }

    fun setQrSuccess(success: Boolean, qrData: ZEMTQrData? = null) {
        _qrSuccess.update { success }
        this.qrData = qrData
    }

    fun setQrError(error: Boolean) {
        _qrError.update { error }
        this.qrData = null
    }
}