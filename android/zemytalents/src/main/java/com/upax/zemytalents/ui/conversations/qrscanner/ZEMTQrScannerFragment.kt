package com.upax.zemytalents.ui.conversations.qrscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.zxing.BarcodeFormat
import com.upax.zemytalents.domain.models.conversations.ZEMTQrData
import com.upax.zemytalents.ui.ZEMTHostActivity

class ZEMTQrScannerFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    private val viewModel: ZEMTQrScannerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideTopAppBar()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                if (uiState.qrSuccess || uiState.qrError) setScannerResult()
                ZEMTQrScannerFragment(
                    modifier = Modifier.systemBarsPadding(),
                    navigateBack = { findNavController().navigateUp() },
                    bindScanner = ::bindScanner,
                    showNotValidQr = uiState.qrNotValid
                )
            }
        }
    }

    private fun setScannerResult() {
        findNavController().navigateUp()
        setFragmentResult(ON_QR_SCAN, bundleOf(ON_QR_SCAN to viewModel.qrData))
    }

    fun bindScanner(scanner: CodeScannerView) {
        codeScanner = CodeScanner(requireActivity(), scanner)
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE)
        codeScanner.isAutoFocusEnabled = true
        codeScanner.decodeCallback = DecodeCallback { result ->
            val qrResult = ZEMTQrData.parseDataFromQr(result.text)
            requireActivity().runOnUiThread {
                if (qrResult != null)
                    if (qrResult.isDateValid) viewModel.setQrSuccess(true, qrResult)
                    else viewModel.setQrError(true)
                else viewModel.setShowNotValidQr(true)
            }
        }
    }


    @Preview
    @Composable
    private fun QRScannerPreview() {
        ZEMTQrScannerFragment(
            modifier = Modifier.systemBarsPadding(),
            bindScanner = {},
            navigateBack = {})
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun hideTopAppBar() {
        (activity as? ZEMTHostActivity)?.hideAppBar()
    }

    companion object {
        const val ON_QR_SCAN = "onQrScan"
    }
}