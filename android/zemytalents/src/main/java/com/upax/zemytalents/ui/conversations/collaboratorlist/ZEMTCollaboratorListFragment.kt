package com.upax.zemytalents.ui.conversations.collaboratorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zcinterceptor.common.ZCIPermissionCatalog
import com.upax.zcinterceptor.domain.model.ZCICommunicationModel
import com.upax.zcinterceptor.navigation.ZCIDestination
import com.upax.zcinterceptor.navigation.ZCINavigation
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.alerts.ZEMTCollaboratorListDialogs
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlert
import com.upax.zemytalents.ui.conversations.collaboratorlist.composables.ZEMTTipsAlertItemMock
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTMakeConversationUiData
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import com.upax.zcdesignsystem.R as DesignSystem

class ZEMTCollaboratorListFragment : Fragment() {
    private val viewModel: ZEMTCollaboratorListViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.COLLABORATOR_LIST)
    }
    private val navArgs: ZEMTCollaboratorListFragmentArgs by navArgs()
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(
            childFragmentManager,
            requireContext()
        )
    }
    private var collaboratorId: String = String.EMPTY

    private fun showLoader() {
        if (!loader.isAdded) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        if (loader.isAdded) loader.dismiss()
    }

    private fun showErrorDialog() {
        errorDialog.showErrorDialog(requestKey = SERVICE_ERROR)
    }

    private fun setResultListeners() {
        setSimpleDialogResultListener(ZEMTCollaboratorListDialogs.RequestKeys.CONTINUE_PROGRESS) { _, accepted ->
            if (accepted) viewModel.navigateToMakeConversation()
            else viewModel.setResetProgress(true)
        }

        setSimpleDialogResultListener(SERVICE_ERROR) { _, _ ->
            viewModel.retry()
        }

        setSimpleDialogResultListener(NO_TALENTS_COMPLETED) { _, _ ->
            val communicationModel = ZCICommunicationModel(
                ZCIPermissionCatalog.WHAT_ZEUS, mutableMapOf(
                    ACTION_WHAT_ZEUS to ACTION_OPEN_CONVERSATION,
                    COLLABORATOR_ID to collaboratorId,
                    KEY_RETURN_TO_MODULE to true
                )
            )
            ZCINavigation.navigateTo(requireContext(), ZCIDestination.ZEUS_CHAT, communicationModel)
        }
    }

    private fun showNoTalentsCompletedDialog(selectedCollaboratorName: String, collaboratorId: String) {
        this.collaboratorId = collaboratorId
        errorDialog.showErrorDialog(
            requestKey = NO_TALENTS_COMPLETED,
            message = getString(
                R.string.zemt_dialog_message_remind_talent_test,
                selectedCollaboratorName
            ),
            positiveButtonText = getString(R.string.zemt_dialog_button_remind_talent_test),
            lottie = ZCDSLottieCatalog.Info
        )
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
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.isLoading) showLoader() else hideLoader()
                if (uiState.isError) showErrorDialog()
                if (uiState.showCarrousel) ZEMTTipsAlert(
                    onDismissRequest = { viewModel.setShowCarrousel(false) },
                    tipsList = ZEMTTipsAlertItemMock.getCollaboratorsCarrouselSlides(
                        LocalContext.current,
                        slide2 = uiState.captions.carrouselSlide2,
                        slide4 = uiState.captions.carrouselSlide4
                    )
                )
                handleMakeConversationNavigation(uiState.makeConversationData)
                setTitle(uiState.captions.collaboratorsCaption)

                ZEMTCollaboratorListView(
                    collaboratorList = uiState.collaboratorList,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_medium))
                        .padding(top = dimensionResource(DesignSystem.dimen.zcds_margin_padding_size_small)),
                    onCollaboratorClick = ::navigation,
                    viewType = navArgs.viewType,
                    captions = uiState.captions,
                    noTalentsCompletedAction = ::showNoTalentsCompletedDialog,
                    onSearchClick = ::navigateToSearchScreen
                )
            }
        }
    }

    private fun handleMakeConversationNavigation(makeConversationState: ZEMTMakeConversationUiData) =
        with(makeConversationState) {
            if (navigateToMakeConversation) {
                navigateToMakeConversation(id = collaboratorId, name = collaboratorName)
                viewModel.resetMakeConversationState()
            } else if (resetProgress) {
                viewModel.resetCollaboratorProgress(collaboratorId = collaboratorId)
            } else if (showResetDialog) {
                ZEMTCollaboratorListDialogs(
                    childFragmentManager,
                    requireContext()
                ).showContinueProgressDialog()
            }
        }

    private fun navigateToMakeConversation(id: String, name: String) {
        findNavController().navigate(
            ZEMTCollaboratorListFragmentDirections.goToMakeConversation(
                collaboratorId = id,
                collaboratorName = name,
            )
        )
    }

    private fun navigation(id: String, name: String, profileImageUrl: String) {
        if (navArgs.viewType == ZEMTCollaboratorListViewType.MAKE_CONVERSATION) {
            viewModel.onStartConversation(collaboratorId = id, collaboratorName = name)
        } else {
            findNavController().navigate(
                ZEMTCollaboratorListFragmentDirections.goToCollaboratorDetailFragment(
                    collaboratorId = id,
                    collaboratorName = name,
                    collaboratorProfileImageUrl = profileImageUrl
                )
            )
        }
    }

    private fun setTitle(collaboratorSubtitle: String) {
        val isMakeConversationType =
            navArgs.viewType == ZEMTCollaboratorListViewType.MAKE_CONVERSATION
        var title = getString(R.string.zemt_talent_menu_converstions)
        var subtitle = collaboratorSubtitle
        if (isMakeConversationType) {
            title = getString(R.string.zemt_my_talents)
            subtitle = getString(R.string.zemt_conversations_start)
        }
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle(
            title = title,
            subtitle = subtitle,
            style = ZCDSTopAppBar.TitlesStyle.Medium,
            iconAction = if (isMakeConversationType) null else Pair(DesignSystem.drawable.zcds_ic_information_circle_outlined)
            { viewModel.setShowCarrousel(true) }
        )
    }

    private fun navigateToSearchScreen() {
        findNavController().navigate(
            ZEMTCollaboratorListFragmentDirections
                .actionZEMTCollaboratorsFragmentToZEMTSearcherFragment(viewType = navArgs.viewType)
        )
    }

    companion object {
        private const val SERVICE_ERROR = "serviceError"
        private const val NO_TALENTS_COMPLETED = "noTalentsCompleted"
        private const val ACTION_WHAT_ZEUS = "action"
        private const val ACTION_OPEN_CONVERSATION = "OPEN_CONVERSATION"
        private const val KEY_RETURN_TO_MODULE = "return"
        private const val COLLABORATOR_ID = "zeus_id"
    }
}