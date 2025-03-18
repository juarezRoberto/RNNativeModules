package com.upax.zemytalents.ui.advice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.utils.observeThemeManagerForStatusBarColor
import com.upax.zcdesignsystem.utils.setStatusBarColor
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zcdesignsystem.R as RDS

internal class ZEMTAdviceFragment : Fragment() {

    private val viewModel: ZEMTAdviceViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.ADVICE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideTopAppBar()

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTAdviceScreen(
                    onContinue = ::navigateToHomeScreen,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveViewedIntroduction()
    }

    private fun hideTopAppBar() {
        (activity as ZEMTHostActivity).hideAppBar()
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(resources.getColor(RDS.color.zcds_white, null))
    }

    override fun onPause() {
        super.onPause()
        observeThemeManagerForStatusBarColor()
    }


    private fun navigateToHomeScreen() {
        findNavController().navigate(
            ZEMTAdviceFragmentDirections
                .actionZEMTAdviceFragmentToZEMTHomeDiscoverFragment()
        )
    }

    @Preview
    @Composable
    private fun ZEMTAdviceScreenPreview() {
        ZEMTAdviceScreen(modifier = Modifier.fillMaxSize(), onContinue = {})
    }
}