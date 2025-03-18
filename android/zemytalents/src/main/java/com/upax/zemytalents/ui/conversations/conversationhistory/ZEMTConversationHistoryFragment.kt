package com.upax.zemytalents.ui.conversations.conversationhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlert
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlertItemMock
import com.upax.zemytalents.ui.conversations.conversationhistory.composable.ZEMTEmptyConversation
import com.upax.zemytalents.ui.conversations.conversationhistory.model.ZEMTConversationState
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import com.upax.zcdesignsystem.R as DesignSystem

internal class ZEMTConversationHistoryFragment : Fragment() {
    private val args: ZEMTConversationHistoryFragmentArgs by navArgs()
    private val viewModel: ZEMTConversationViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.CONVERSATION_HISTORY)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    private fun showLoader() {
        val attached = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
        if (attached == null) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        if (loader.isAdded) loader.dismiss()
    }

    private fun showErrorDialog() {
        ZEMTErrorDialog(
            childFragmentManager,
            requireContext()
        ).showErrorDialog(requestKey = ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS_HISTORY.key)
    }

    private fun setResultListeners() {
        setSimpleDialogResultListener(ZEMTConversationsErrorType.ERROR_RETRIEVING_CONVERSATIONS_HISTORY.key) { _, _ ->
            getConversationsHistory()
        }
    }

    private fun getConversationsHistory() {
        viewModel.getConversationsHistory(args.collaboratorId, args.bossId, args.conversationId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setResultListeners()
        updateTitle()
        getConversationsHistory()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.isLoading) showLoader() else hideLoader()
                if (uiState.isError) showErrorDialog()
                if (uiState.showTipsAlert) ZEMTTipsAlert(
                    onDismissRequest = { viewModel.hideTipsAlert() },
                    tipsList = ZEMTTipsAlertItemMock.getTalentResumeTips(
                        requireContext(),
                        tipDescription = uiState.tipAlertText
                    )
                )

                val conversationList = uiState.conversations.map { conversation ->
                    if (conversation.realized) ZEMTConversationState.Completed(
                        date = conversation.startDate,
                        comment = conversation.comment,
                        phrase = conversation.phrase.description
                    ) else ZEMTConversationState.Uncompleted(
                        date = conversation.startDate,
                        title = stringResource(R.string.zemt_conversation_history_not_realized_title),
                        message = stringResource(R.string.zemt_conversation_history_not_realized_messsage),
                    )
                }

                if (conversationList.isNotEmpty()) ZEMTConversationView(
                    conversationList = conversationList,
                    modifier = Modifier.navigationBarsPadding()
                ) else ZEMTEmptyConversation()
            }
        }
    }

    private fun updateTitle() {
        (activity as? ZEMTHostActivity)?.updateAppBarTitle(
            title = args.title,
            subtitle = args.subtitle,
            style = ZCDSTopAppBar.TitlesStyle.Medium,
            iconAction = DesignSystem.drawable.zcds_ic_information_circle_outlined to { viewModel.showTipsAlert() }
        )
    }
}