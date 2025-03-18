package com.upax.zemytalents.ui.talentmenu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.common.ZEMTQrFunctions.shareQrCode
import com.upax.zemytalents.common.ZEMTStringExtensions.capitalizeText
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType
import com.upax.zemytalents.ui.conversations.qr.ZEMTQrProfileInfo
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTConversationsErrorType
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import com.upax.zemytalents.ui.talentmenu.model.ZEMTMenuTalentNavigation
import kotlinx.coroutines.launch
import com.upax.zcdesignsystem.R as DesignSystem

class ZEMTMenuTalentFragment : Fragment() {
    private val viewModel: ZEMTMenuTalentViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.MENU_TALENT)
    }

    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(
            childFragmentManager,
            requireContext()
        )
    }

    private fun showLoader() {
        childFragmentManager.executePendingTransactions()
        val isAttached =
            childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG) != null
        if (!isAttached) loader.show(
            childFragmentManager,
            ZCDSLoaderDialogFragment.TAG
        )
    }

    private fun hideLoader(navigateToTalents: Boolean, userData: ZCSIUser) {
        lifecycleScope.launch {
            val isAttached =
                childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG) != null
            if (isAttached) loader.dismissAllowingStateLoss()
            if (navigateToTalents) {
                DISMISS_SCREEN = true
                navigateTo(ZEMTMenuTalentNavigation.GO_MY_TALENTS, userData)
            }
        }
    }

    private fun setResultListeners() {
        ZEMTConversationsErrorType.entries.forEach { errorType ->
            setSimpleDialogResultListener(errorType.key) { _, _ ->
                when (errorType) {
                    ZEMTConversationsErrorType.ERROR_RETRIEVING_SERVICE_TEXT -> viewModel.fetchServiceText()
                    ZEMTConversationsErrorType.ERROR_RETRIEVING_COLLABORATORS_IN_CHARGE -> viewModel.fetchCollaboratorsInCharge()
                    ZEMTConversationsErrorType.ERROR_RETRIEVING_TALENTS_COMPLETED -> viewModel.fetchTalentsCompletedById()
                    else -> Unit
                }
            }
        }
    }

    private fun showErrorMenuDialog(errorType: ZEMTConversationsErrorType) {
        errorDialog.showErrorDialog(requestKey = errorType.key)
    }

    private fun fetchAllServices() {
        viewModel.fetchServiceText()
        viewModel.fetchCollaboratorsInCharge()
        viewModel.fetchTalentsCompletedById()
    }

    override fun onResume() {
        super.onResume()
        if (DISMISS_SCREEN) {
            DISMISS_SCREEN = false
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("REACT", "ZEMTMenuTalentFragment - onCreateView")
        setResultListeners()
        setTitle()
        fetchAllServices()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val userData = uiState.user
                if (uiState.isLoading) showLoader() else {
                    hideLoader(
                        uiState.redirectToTalents,
                        userData
                    )
                }

                if (uiState.errorType != null) showErrorMenuDialog(uiState.errorType!!)

                if (uiState.showQrCode) ZEMTQrProfileInfo(
                    userData = userData,
                    onDismissRequest = { viewModel.hideQrCode() },
                    onShareAction = {
                        shareQrCode(userData)
                    })

                if (uiState.isTalentsCompleted) enableQr()
                if (uiState.isLoading.not() && uiState.redirectToTalents.not()) ZEMTMenuTalentView(
                    userName = userData.name.capitalizeText(),
                    description = uiState.homeCaption.homeCaption,
                    navigateTo = { navigateTo(it, userData) },
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp),
                    options = uiState.menuOptionList,
                    canMakeConversation = uiState.canMakeConversation
                )
            }
        }
    }

    private fun navigateTo(navigation: ZEMTMenuTalentNavigation, userData: ZCSIUser) {
        val action = when (navigation) {
            ZEMTMenuTalentNavigation.GO_MY_TALENTS -> ZEMTMenuTalentFragmentDirections.navigateToStartFragment(
                collaboratorId = userData.zeusId,
                collaboratorName = userData.name,
                collaboratorProfileUrl = userData.photo
            )

            ZEMTMenuTalentNavigation.GO_COLLABORATORS -> ZEMTMenuTalentFragmentDirections.goToCollaboratorsFragment()
            ZEMTMenuTalentNavigation.GO_CONVERSATION_TYPES -> ZEMTMenuTalentFragmentDirections.goToConversationTypes()
            ZEMTMenuTalentNavigation.MAKE_CONVERSATION -> {
                if (viewModel.skipOnboarding())
                    ZEMTMenuTalentFragmentDirections.goToCollaboratorsFragment(
                        ZEMTCollaboratorListViewType.MAKE_CONVERSATION
                    )
                else ZEMTMenuTalentFragmentDirections.goToConversationOnboarding()
            }

            ZEMTMenuTalentNavigation.NONE -> null
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    private fun enableQr() {
        (requireActivity() as ZEMTHostActivity).updateAppBarTitle(
            getString(R.string.zemt_my_talents),
            iconAction = DesignSystem.drawable.zcds_ic_qrcode_solid to { viewModel.showQrCode() })
    }

    private fun setTitle() {
        (requireActivity() as ZEMTHostActivity).updateAppBarTitle(getString(R.string.zemt_my_talents))
    }

    companion object {
        private var DISMISS_SCREEN = false
    }
}