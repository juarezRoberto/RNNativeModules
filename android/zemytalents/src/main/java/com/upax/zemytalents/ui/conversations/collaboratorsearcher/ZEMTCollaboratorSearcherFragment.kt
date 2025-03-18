package com.upax.zemytalents.ui.conversations.collaboratorsearcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcinterceptor.common.ZCIPermissionCatalog
import com.upax.zcinterceptor.domain.model.ZCICommunicationModel
import com.upax.zcinterceptor.navigation.ZCIDestination
import com.upax.zcinterceptor.navigation.ZCINavigation
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.alerts.ZEMTCollaboratorListDialogs
import kotlinx.coroutines.launch

internal class ZEMTCollaboratorSearcherFragment : Fragment() {

    private val viewModel: ZEMTCollaboratorSearcherViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.COLLABORATOR_SEARCHER)
    }
    private val dialogs by lazy {
        ZEMTCollaboratorSearcherDialogs(childFragmentManager, requireContext())
    }
    private val collaboratorListDialogs by lazy {
        ZEMTCollaboratorListDialogs(childFragmentManager, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? ZEMTHostActivity)?.hideAppBar()
        setDialogsListeners()
        collectCollaboratorActionsState()
        collectMakeConversationState()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                val collaborators = viewModel.collaborators.collectAsStateWithLifecycle()

                ZEMTCollaboratorSearcherScreen(
                    collaborators = collaborators.value,
                    onSearchTextChange = viewModel::onSearchTextChange,
                    onCollaboratorClick = viewModel::onCollaboratorSelected,
                    onBackPressed = { findNavController().popBackStack() }
                )
            }
        }
    }

    private fun setDialogsListeners() {
        setSimpleDialogResultListener(ZEMTCollaboratorSearcherDialogs.KEY_NO_COMPLETED_TALENTS) { _, _ ->
            val collaborator = viewModel.collaboratorSelected
                ?: return@setSimpleDialogResultListener
            val communicationModel = ZCICommunicationModel(
                ZCIPermissionCatalog.WHAT_ZEUS, mutableMapOf(
                    ACTION_WHAT_ZEUS to ACTION_OPEN_CONVERSATION,
                    COLLABORATOR_ID to collaborator.collaboratorId,
                    KEY_RETURN_TO_MODULE to true
                )
            )
            ZCINavigation.navigateTo(requireContext(), ZCIDestination.ZEUS_CHAT, communicationModel)
            viewModel.resetCollaboratorSelected()
        }
        setSimpleDialogResultListener(
            ZEMTCollaboratorListDialogs.RequestKeys.CONTINUE_PROGRESS
        ) { _, accepted ->
            if (accepted) viewModel.navigateToMakeConversation()
            else viewModel.setResetProgress(true)
        }
    }

    private fun collectCollaboratorActionsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.collaboratorAction.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                handleCollaboratorActionState(it)
            }
        }
    }

    private fun handleCollaboratorActionState(state: ZEMTCollaboratorSearcherActionsState?) {
        val collaborator = viewModel.collaboratorSelected ?: return
        when (state) {
            is ZEMTCollaboratorSearcherActionsState.NavigateToDetail -> {
                navigateToCollaboratorDetail(collaborator)
                viewModel.restCollaboratorAction(); viewModel.resetCollaboratorSelected()
            }

            is ZEMTCollaboratorSearcherActionsState.NavigateToMakeConversation -> {
                viewModel.onStartConversation(
                    collaboratorId = collaborator.collaboratorId,
                    collaboratorName = collaborator.name
                )
                viewModel.restCollaboratorAction(); viewModel.resetCollaboratorSelected()
            }

            is ZEMTCollaboratorSearcherActionsState.TalentsNoCompleted -> {
                dialogs.showCollaboratorHasNoCompletedTalents(collaborator.name)
                viewModel.restCollaboratorAction()
            }

            null -> Unit
        }
    }

    private fun navigateToCollaboratorDetail(collaborator: ZEMTCollaboratorInCharge) {
        findNavController().navigate(
            ZEMTCollaboratorSearcherFragmentDirections
                .actionZEMTSearcherFragmentToZEMTCollaboratorDetailFragment(
                    collaboratorId = collaborator.collaboratorId,
                    collaboratorName = collaborator.name,
                    collaboratorProfileImageUrl = collaborator.photoUrl
                )
        )
    }

    private fun collectMakeConversationState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.makeConversationState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                with(it) {
                    if (navigateToMakeConversation) {
                        navigateToMakeConversation(id = collaboratorId, name = collaboratorName)
                        viewModel.resetMakeConversationState()
                    } else if (resetProgress) {
                        viewModel.resetCollaboratorProgress(collaboratorId = collaboratorId)
                    } else if (showResetDialog) {
                        collaboratorListDialogs.showContinueProgressDialog()
                    }
                }
            }
        }
    }

    private fun navigateToMakeConversation(id: String, name: String) {
        findNavController().navigate(
            ZEMTCollaboratorSearcherFragmentDirections.actionZEMTSearcherFragmentToZEMTMakeConversationFragment(
                collaboratorId = id,
                collaboratorName = name,
            )
        )
    }

    companion object {
        private const val ACTION_WHAT_ZEUS = "action"
        private const val ACTION_OPEN_CONVERSATION = "OPEN_CONVERSATION"
        private const val KEY_RETURN_TO_MODULE = "return"
        private const val COLLABORATOR_ID = "zeus_id"
    }

}