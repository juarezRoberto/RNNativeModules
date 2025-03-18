package com.upax.zemytalents.ui.modules.apply.home

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
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.permissions.ZCDSPermission
import com.upax.zcdesignsystem.permissions.ZCDSPermissionsHelper
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity

internal class ZEMTHomeApplyFragment : Fragment() {

    private val viewModel: ZEMTHomeApplyViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.HOME_APPLY)
    }
    private val permissionsHelper: ZCDSPermissionsHelper by lazy { ZCDSPermissionsHelper(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val modulesState by viewModel.modulesState.collectAsStateWithLifecycle()
                ZEMTHomeApplyScreen(
                    modules = modulesState,
                    onNavigateTo = { navigateToApplySurveyScreen() }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle(title = getString(R.string.zemt_my_talents))
    }

    private fun navigateToApplySurveyScreen() {
        permissionsHelper.requestPermissions(ZCDSPermission.LOCATION, onGranted = {
            if (it) {
                findNavController().navigate(
                    ZEMTHomeApplyFragmentDirections.actionZEMTHomeApplyFragmentToZEMTSurveyApplyFragment()
                )
            }
        })
    }
}