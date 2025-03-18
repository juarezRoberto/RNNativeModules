package com.upax.zemytalents.ui.conversations.collaboratordetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
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
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorDetailMenuMock
import com.upax.zemytalents.ui.conversations.collaboratordetail.model.ZEMTCollaboratorDetailNavigation
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import kotlinx.coroutines.launch

class ZEMTCollaboratorDetailFragment : Fragment() {
    private val args: ZEMTCollaboratorDetailFragmentArgs by navArgs()
    private val viewModel: ZEMTCollaboratorDetailViewModel by viewModels() {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.COLLABORATOR_DETAIL)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        updateTitle()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.isLoading) showLoader() else hideLoader()
                ZEMTCollaboratorDetailView(
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    ),
                    navigateTo = { navigateTo(it, uiState.leaderId) },
                    menuSectionList = ZEMTCollaboratorDetailMenuMock.getMenuList(
                        getString = { context.getString(it) },
                        conversationList = uiState.conversationList
                    )
                )
            }
        }
    }

    private fun navigateTo(navigation: ZEMTCollaboratorDetailNavigation, leaderId: String) {
        when (navigation) {
            is ZEMTCollaboratorDetailNavigation.GoToConversation -> findNavController().navigate(
                ZEMTCollaboratorDetailFragmentDirections.goToConversation(
                    title = args.collaboratorName,
                    subtitle = navigation.conversationTitle,
                    collaboratorId = args.collaboratorId,
                    bossId = leaderId,
                    conversationId = navigation.conversationId
                )
            )

            is ZEMTCollaboratorDetailNavigation.GoToTalent -> findNavController().navigate(
                ZEMTCollaboratorDetailFragmentDirections.goToTalentsResume(
                    collaboratorName = args.collaboratorName,
                    collaboratorId = args.collaboratorId,
                    collaboratorProfileImageUrl = args.collaboratorProfileImageUrl,
                    viewType = ZEMTTalentsResumeType.COLLABORATOR_TALENTS
                )
            )

            else -> Unit
        }
    }

    private fun updateTitle() {
        (activity as? ZEMTHostActivity)?.updateAppBarTitle(
            title = getString(R.string.zemt_talent_menu_collaborators),
            subtitle = args.collaboratorName,
            style = ZCDSTopAppBar.TitlesStyle.Medium
        )
    }
}