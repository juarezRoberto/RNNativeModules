package com.upax.zemytalents.ui.modules.confirm.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.permissions.ZCDSPermission
import com.upax.zcdesignsystem.permissions.ZCDSPermissionsHelper
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.modules.confirm.home.models.ZEMTHomeConfirmServiceState
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_DOWNLOAD_SURVEY_ERROR
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch

internal class ZEMTHomeConfirmFragment : Fragment() {

    private val args: ZEMTHomeConfirmFragmentArgs by navArgs()

    private val viewModel: ZEMTHomeConfirmViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.HOME_CONFIRM)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private val alertError: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(
            childFragmentManager,
            requireContext()
        )
    }
    private lateinit var permissionsHelper: ZCDSPermissionsHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val serviceState by viewModel.serviceState.collectAsStateWithLifecycle()
                val progressState by viewModel.modulesWithProgressState.collectAsStateWithLifecycle(
                    emptyList()
                )

                if (serviceState is ZEMTHomeConfirmServiceState.Success) {
                    ZEMTHomeConfirmScreen(
                        uiState = (serviceState as ZEMTHomeConfirmServiceState.Success).uiState,
                        modules = progressState,
                        shouldSkipAnimations = !args.fromDiscoverSurvey,
                        onNavigateToConfirmSurveyScreen = { navigateToConfirmSurveyScreen(it) }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ZEMTHostActivity).updateAppBarTitle(title = getString(R.string.zemt_my_talents))
        collectUiState()
        setListeners()
        permissionsHelper = ZCDSPermissionsHelper(this)
        if (viewModel.serviceState.value is ZEMTHomeConfirmServiceState.Idle) {
            viewModel.getTalents()
        }
    }

    private fun setListeners() {
        setSimpleDialogResultListener(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR) { _, _ ->
            findNavController().navigateUp()
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.serviceState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    ZEMTHomeConfirmServiceState.Loading -> showLoader()

                    ZEMTHomeConfirmServiceState.Error -> {
                        hideLoader()
                        alertError.showErrorDialog(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR)
                    }

                    is ZEMTHomeConfirmServiceState.Success -> hideLoader()
                    else -> Unit
                }
            }
        }
    }

    private fun navigateToConfirmSurveyScreen(modules: List<ZEMTTalent>) {
        permissionsHelper.requestPermissions(ZCDSPermission.LOCATION, onGranted = {
            if (it) {
                val modulesJson = Gson().toJson(modules)
                findNavController().navigate(
                    ZEMTHomeConfirmFragmentDirections.navigateToConfirmSurveyFragment(modulesJson)
                )
            }
        })
    }

    private fun showLoader() {
        if (!loader.isAdded) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        if (loader.isAdded) loader.dismiss()
    }
}