package com.upax.zemytalents.ui.conversations.talentsresume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.common.ZEMTQrFunctions.shareQrCode
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlert
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlertItemMock
import com.upax.zemytalents.ui.conversations.qr.ZEMTQrProfileInfo
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch
import com.upax.zcdesignsystem.R as DesignSystem

class ZEMTTalentsResumeFragment : Fragment() {
    private val args: ZEMTTalentsResumeFragmentArgs by navArgs()

    private val viewModel: ZEMTTalentsResumeViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.TALENTS_RESUME)
    }

    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(childFragmentManager, requireContext())
    }

    private fun showLoader() {
        val attached = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
        if (attached == null) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        lifecycleScope.launch {
            val attached = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
            if (attached != null) loader.dismiss()
        }
    }

    private fun setResultListeners() {
        ZEMTConversationsErrorType.entries.forEach { errorType ->
            setSimpleDialogResultListener(errorType.key) { _, _ ->
                when (errorType) {
                    ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS -> viewModel.fetchTalents(
                        args.collaboratorId
                    )

                    ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS -> viewModel.fetchConversations(
                        args.collaboratorId
                    )

                    ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS_FINISHED -> viewModel.fetchTalentsFinished(
                        args.collaboratorId
                    )

                    else -> Unit
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.fetchTalents(args.collaboratorId)
        viewModel.setLocalData(
            collaboratorName = args.collaboratorName,
            userProfileUrl = args.collaboratorProfileImageUrl,
            tabList = ZEMTTabOptionType.entries,
            viewType = args.viewType
        )
        viewModel.fetchConversations(args.collaboratorId)
        if (args.viewType == ZEMTTalentsResumeType.MY_TALENTS) viewModel.fetchTalentsFinished(args.collaboratorId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        updateTitle(false)
        setResultListeners()
        initViewModel()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                var showTipsAlert by remember { mutableStateOf(false) }

                if (uiState.isLoading) showLoader() else hideLoader()
                if (uiState.errorType != null) errorDialog.showErrorDialog(uiState.errorType?.key.orEmpty())
                if (showTipsAlert) ZEMTTipsAlert(
                    onDismissRequest = { showTipsAlert = false },
                    tipsList = ZEMTTipsAlertItemMock.getTalentResumeTips(requireContext(), tipDescription = uiState.tipAlertText)
                )
                if (uiState.enableQrCode) updateTitle(showQrCode = true)
                if (uiState.showQrCode) ZEMTQrProfileInfo(
                    userData = uiState.userData,
                    onDismissRequest = { viewModel.hideQrCode() },
                    onShareAction = { shareQrCode(uiState.userData) })

                ZEMTTalentsResumeView(
                    userInfo = uiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    onConversationClick = { navigateToConversation(it, uiState.bossId) },
                    onInfoButtonClick = { showTipsAlert = true },
                    onSelectedTabChange = viewModel::setSelectedTab
                )
            }
        }
    }

    private fun navigateToConversation(conversation: ZEMTConversation, bossId: String) {
        findNavController().navigate(
            ZEMTTalentsResumeFragmentDirections.fromTalentsResumeGoToConversationHistory(
                title = args.collaboratorName,
                subtitle = if (args.viewType == ZEMTTalentsResumeType.COLLABORATOR_TALENTS) conversation.name else "Taletos y conversaciones",
                collaboratorId = args.collaboratorId,
                bossId = bossId,
                conversationId = conversation.conversationId
            )
        )
    }

    private fun updateTitle(showQrCode: Boolean) {
        val hostActivity = activity as? ZEMTHostActivity
        val iconAction: Pair<Int, (View) -> Unit>? =
            if (showQrCode) Pair(
                DesignSystem.drawable.zcds_ic_qrcode_solid
            ) { viewModel.showQrCode() } else null

        if (args.viewType == ZEMTTalentsResumeType.COLLABORATOR_TALENTS) {
            hostActivity?.updateAppBarTitle(
                title = args.collaboratorName,
                subtitle = getString(R.string.zemt_dominant_no_dominant_talents),
                style = ZCDSTopAppBar.TitlesStyle.Medium,
                iconAction = iconAction
            )
        } else {
            hostActivity?.updateAppBarTitle(
                title = getString(R.string.zemt_my_talents),
                subtitle = String.EMPTY,
                style = ZCDSTopAppBar.TitlesStyle.Medium,
                iconAction = iconAction
            )
        }
    }

    enum class ZEMTTabOptionType(val title: String) {
        DOMINANT("Dominantes"),
        NO_DOMINANT("No dominantes"),
        CONVERSATIONS("Conversaciones"),
    }
}