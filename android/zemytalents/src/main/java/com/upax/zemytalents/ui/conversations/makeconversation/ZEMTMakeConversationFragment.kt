package com.upax.zemytalents.ui.conversations.makeconversation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.permissions.ZCDSPermission
import com.upax.zcdesignsystem.permissions.ZCDSPermissionsHelper
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.conversations.ZEMTQrData
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.makeconversation.model.ZEMTMakeConversationProgress
import com.upax.zemytalents.ui.conversations.qrscanner.ZEMTQrScannerFragment
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog

internal class ZEMTMakeConversationFragment : Fragment() {
    private val args: ZEMTMakeConversationFragmentArgs by navArgs()
    private val viewModel: ZEMTMakeConversationViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.MAKE_CONVERSATION)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    private val errorDialog: ZEMTErrorDialog by lazy { ZEMTErrorDialog(childFragmentManager, requireContext()) }

    private fun showLoader() {
        val attached = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
        if (attached == null) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        val attached = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
        if (attached != null) loader.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val key = ZEMTQrScannerFragment.ON_QR_SCAN
        setFragmentResultListener(key) { _, bundle ->
            val qrData: ZEMTQrData? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, ZEMTQrData::class.java)
                } else {
                    bundle.getParcelable(key)
                }
            if (qrData != null) viewModel.saveConversation() else showQrErrorDialog()
        }
    }

    private fun showQrErrorDialog() {
        errorDialog.showErrorDialog(message = R.string.zemt_qr_error_message)
    }

    private fun showErrorDialog() {
        errorDialog.showErrorDialog(
            message = R.string.zemt_qr_error_message,
            requestKey = SERVICE_ERROR
        )
    }

    private fun setResultListeners() {
        setSimpleDialogResultListener(SERVICE_ERROR) { _, _ ->
            viewModel.retryServiceCall(args.collaboratorId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setResultListeners()
        viewModel.onStart(collaboratorId = args.collaboratorId)
        updateTitle()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.isLoading) showLoader() else hideLoader()
                if (uiState.isError) showErrorDialog()
                if (uiState.isSaveConversationError) showQrErrorDialog()
                if (uiState.isSaveConversationSuccess) navigateToSavedConversation()
                val focusManager = LocalFocusManager.current
                val isKeyboardOpen by keyboardAsState()
                if (!isKeyboardOpen) {
                    focusManager.clearFocus(force = false)
                }
                ZEMTMakeConversationView(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus(force = false)
                            }
                        }
                        .navigationBarsPadding()
                        .padding(16.dp),
                    makeConversationData = uiState.conversationData,
                    currentStep = uiState.conversationData.currentStep,
                    nextStep = { nextStep(uiState.conversationData) },
                    prevStep = viewModel::previousStep,
                    onPhraseSelected = { data -> viewModel.setSelectedPhrase(data) },
                    onConversationSelected = { data -> viewModel.setSelectedConversation(data) },
                    onConversationRealized = { data -> viewModel.setConversationRealized(data) },
                    onCommentChanged = { data -> viewModel.setComment(data) },
                    conversationList = uiState.conversationList,
                    phraseList = uiState.phraseList,
                    captions = uiState.captions
                )
            }
        }
    }

    private fun navigateToSavedConversation() {
        findNavController().navigate(
            ZEMTMakeConversationFragmentDirections.fromMakeConversationToConversationSaved()
        )
        viewModel.resetSaveConversationState()
    }

    private fun nextStep(makeConversationData: ZEMTMakeConversationProgress) {
        if (makeConversationData.currentStep.isSummary()) {
            if (makeConversationData.isConversationMade != null && makeConversationData.comment.isNotEmpty()) {
                if (makeConversationData.isConversationMade == false) {
                    findNavController().navigateUp()
                } else {
                    ZCDSPermissionsHelper(this).requestPermissions(
                        ZCDSPermission.CAMERA,
                        onGranted = { isGranted ->
                            if (isGranted) {
                                viewModel.nextStep(args.collaboratorId)
                                findNavController().navigate(ZEMTMakeConversationFragmentDirections.fromMakeConversationGoToQRLector())
                            }
                        })
                }
            }
        } else viewModel.nextStep(args.collaboratorId)
    }

    @Composable
    private fun keyboardAsState(): State<Boolean> {
        val keyboardState = remember { mutableStateOf(false) }
        val view = LocalView.current
        val viewTreeObserver = view.viewTreeObserver
        DisposableEffect(viewTreeObserver) {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                keyboardState.value = ViewCompat.getRootWindowInsets(view)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            }
            viewTreeObserver.addOnGlobalLayoutListener(listener)
            onDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
        }
        return keyboardState
    }

    private fun updateTitle() {
        (activity as? ZEMTHostActivity)?.updateAppBarTitle(
            title = getString(R.string.zemt_conversations_start),
            subtitle = args.collaboratorName,
            style = ZCDSTopAppBar.TitlesStyle.Medium
        )
    }

    companion object {
        const val SERVICE_ERROR = "SERVICE_ERROR"
    }
}