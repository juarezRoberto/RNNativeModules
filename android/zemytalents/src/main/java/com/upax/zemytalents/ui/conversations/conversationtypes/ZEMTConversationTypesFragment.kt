package com.upax.zemytalents.ui.conversations.conversationtypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences
import com.upax.zemytalents.di.ZEMTDataProvider
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import com.upax.zemytalents.ui.shared.composables.ZEMTBottomSheet
import kotlinx.coroutines.launch
import com.upax.zcdesignsystem.R as DesignSystem

internal class ZEMTConversationTypesFragment : Fragment() {
    private val viewModel: ZEMTConversationTypesViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.CONVERSATION_TYPES)
    }

    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    private val preferences: ZEMTLocalPreferences by lazy {
        ZEMTDataProvider.provideLocalPreferences(requireContext())
    }

    private fun showLoader() {
        if (!loader.isAdded) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader(tipMessage: String) {
        lifecycleScope.launch {
            if (loader.isAdded) loader.dismiss()
            if (preferences.conversationTypesTipsShown.not() && tipMessage.isNotEmpty()) showAlertDialog(tipMessage = tipMessage)
        }
    }

    private fun showErrorMenuDialog() {
        ZEMTErrorDialog(
            childFragmentManager,
            requireContext()
        ).showErrorDialog(requestKey = ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS.key)
    }

    private fun setResultListeners() {
        setSimpleDialogResultListener(ALERT_DIALOG_TAG) { _, _ ->
            preferences.conversationTypesTipsShown = true
        }

        setSimpleDialogResultListener(ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS.key) { _, _ ->
            showErrorMenuDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setResultListeners()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                if (state.isLoading) showLoader() else hideLoader(state.tipMessage)
                var selectedConversation: ZEMTConversation? by remember { mutableStateOf(null) }
                setTitle(state.tipMessage)

                if (selectedConversation != null) {
                    ZEMTBottomSheet(
                        title = selectedConversation?.name.orEmpty(),
                        description = selectedConversation?.description.orEmpty(),
                        lottieUrl = selectedConversation?.lottieUrl.orEmpty(),
                        onDismissRequest = { selectedConversation = null })
                }
                ZEMTConversationTypesView(
                    conversationList = state.conversationList,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    showBottomSheet = { conversation ->
                        selectedConversation = conversation
                    })
            }
        }
    }

    private fun setTitle(tipMessage: String) {
        (requireActivity() as ZEMTHostActivity).updateAppBarTitle(
            title = getString(R.string.zemt_talent_menu_converstions),
            subtitle = getString(R.string.zemt_talent_menu_converstion_types),
            style = ZCDSTopAppBar.TitlesStyle.Medium,
            iconAction = DesignSystem.drawable.zcds_ic_information_circle_outlined to {
                showAlertDialog(
                    tipMessage
                )
            }
        )
    }

    private fun showAlertDialog(tipMessage: String) {
        ZCDSSimpleDialogFragment
            .Builder(requireContext(), ALERT_DIALOG_TAG)
            .setLottieAnimation(ZCDSLottieCatalog.TipsZeus)
            .setTitle(R.string.zemt_tip_title)
            .setCancelable(false)
            .setMessage(tipMessage)
            .setPositiveButton(R.string.zemt_ok)
            .build()
            .show(childFragmentManager, tag)
    }

    companion object {
        private const val ALERT_DIALOG_TAG = "AlertDialog"
    }
}