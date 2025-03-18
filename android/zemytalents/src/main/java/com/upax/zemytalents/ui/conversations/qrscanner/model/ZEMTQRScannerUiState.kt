package com.upax.zemytalents.ui.conversations.qrscanner.model

data class ZEMTQRScannerUiState(
    val qrNotValid: Boolean = false,
    val qrError: Boolean = false,
    val qrSuccess: Boolean = false
)
