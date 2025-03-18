package com.upax.zemytalents.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_DOWNLOAD_SURVEY_ERROR
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch

internal class ZEMTStartFragment : Fragment() {

    private val viewModel: ZEMTStartViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.START)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(childFragmentManager, requireContext())
    }
    private val navArgs: ZEMTStartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppBar()
        collectUiState()
        setListeners()
        val previousDestination = findNavController().previousBackStackEntry?.destination
        val startFragmentIsStartDestination = previousDestination == null
        if (startFragmentIsStartDestination) {
            viewModel.downloadCaptions()
        } else {
            viewModel.getModules()
        }
    }

    private fun setListeners() {
        setSimpleDialogResultListener(KEY_ERROR_CAPTIONS) { _, _ ->
            viewModel.downloadCaptions()
        }
        setSimpleDialogResultListener(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR) { _, _ ->
            findNavController().navigateUp()
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                when (state) {
                    ZEMTStartUiModel.Idle -> Unit
                    ZEMTStartUiModel.Loading -> showLoader()
                    ZEMTStartUiModel.Error -> {
                        hideLoader()
                        errorDialog.showErrorDialog(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR)
                    }

                    is ZEMTStartUiModel.Success -> {
                        hideLoader()

                        val isApplyModuleCompleted =
                            state.modules.any { it.stage == ZEMTModuleStage.APPLY && it.isComplete }
                        if (isApplyModuleCompleted) navigateToFinalTalentsScreen()
                        else {
                            val isConfirmModuleCompleted =
                                state.modules.any { it.stage == ZEMTModuleStage.CONFIRM && it.isComplete }
                            if (isConfirmModuleCompleted) navigateToHomeApplyScreen()
                            else {
                                val isDiscoverModuleCompleted =
                                    state.modules.any { it.stage == ZEMTModuleStage.DISCOVER && it.isComplete }
                                if (isDiscoverModuleCompleted) navigateToHomeConfirmScreen()
                                else {
                                    if (state.wasIntroductionViewed) {
                                        navigateToHomeDiscoverScreen()
                                    } else {
                                        navigateToIntroductionScreen()
                                    }
                                }
                            }
                        }
                    }

                    ZEMTStartUiModel.ErrorCaptions -> {
                        hideLoader()
                        errorDialog.showErrorDialog(KEY_ERROR_CAPTIONS)
                    }
                    ZEMTStartUiModel.SuccessCaptions -> viewModel.getModules()
                }
            }
        }
    }

    private fun showLoader() {
        if (!loader.isAdded) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        if (loader.isAdded) loader.dismiss()
    }

    private fun hideAppBar() {
        (activity as? ZEMTHostActivity)?.hideAppBar()
    }

    private fun navigateToIntroductionScreen() {
        findNavController().navigate(
            ZEMTStartFragmentDirections.navigateToIntroductionFragment()
        )
    }

    private fun navigateToHomeDiscoverScreen() {
        findNavController().navigate(
            ZEMTStartFragmentDirections.navigateToDiscoverFragment()
        )
    }

    private fun navigateToHomeConfirmScreen() {
        findNavController().navigate(
            ZEMTStartFragmentDirections.navigateToConfirmFragment()
        )
    }

    private fun navigateToHomeApplyScreen() {
        findNavController().navigate(
            ZEMTStartFragmentDirections.navigateToApplyFragment()
        )
    }

    private fun navigateToFinalTalentsScreen() {
        findNavController().navigate(
            ZEMTStartFragmentDirections.fromStartGoToTalentsResume(
                collaboratorName = navArgs.collaboratorName,
                collaboratorId = navArgs.collaboratorId,
                collaboratorProfileImageUrl = navArgs.collaboratorProfileUrl,
                viewType = ZEMTTalentsResumeType.MY_TALENTS
            )
        )
    }

    companion object {
        private const val KEY_ERROR_CAPTIONS = "KEY_ERROR_CAPTIONS"
    }

}