package com.upax.zemytalents.ui.modules.discover.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.permissions.ZCDSPermission
import com.upax.zcdesignsystem.permissions.ZCDSPermissionsHelper
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.ui.ZEMTHostActivity
import kotlinx.coroutines.launch

internal class ZEMTHomeDiscoverFragment : Fragment() {

    private val viewModel: ZEMTHomeDiscoverViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.HOME_DISCOVER)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private lateinit var permissionsHelper: ZCDSPermissionsHelper
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val previousFragmentId = findNavController().previousBackStackEntry?.destination?.id
            if (previousFragmentId == R.id.ZEMTStartFragment) {
                findNavController().navigate(
                    ZEMTHomeDiscoverFragmentDirections
                        .actionZEMTHomeDiscoverFragmentToZEMTMenuTalentFragment()
                )
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ZEMTHomeDiscoverScreen(
                    user = uiState.user,
                    modules = uiState.modules,
                    navigateToDiscoverModule = { navigateToDiscoverModule() },
                    discoverModuleProgress = uiState.progress
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)
        permissionsHelper = ZCDSPermissionsHelper(this)
        (activity as? ZEMTHostActivity)?.updateAppBarTitle(title = getString(R.string.zemt_my_talents))
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it) showLoader() else hideLoader()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it.modules.isNotEmpty() && viewModel.navToDiscoverModuleState.value == null) {
                    viewModel.setNavigateToDiscoverState()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navToDiscoverModuleState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { shouldNavigate ->
                    if (shouldNavigate != null && shouldNavigate) {
                        viewModel.resetNavigateToDiscoverState()
                        navigateToDiscoverModule()
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

    private fun navigateToDiscoverModule() {
        permissionsHelper.requestPermissions(ZCDSPermission.LOCATION, onGranted = {
            if (it) findNavController().navigate(
                ZEMTHomeDiscoverFragmentDirections
                    .actionZEMTHomeDiscoverFragmentToZEMTSurveyDiscoverFragment()
            )
        })
    }
}