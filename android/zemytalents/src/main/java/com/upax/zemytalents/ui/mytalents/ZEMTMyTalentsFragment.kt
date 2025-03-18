package com.upax.zemytalents.ui.mytalents

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
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.modules.apply.talentdetail.ZEMTTalentDetailFragment
import kotlinx.coroutines.launch

class ZEMTMyTalentsFragment : Fragment() {

    private val viewModel: ZEMTMyTalentsViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.FINAL_TALENTS)
    }

    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val serviceState by viewModel.serviceState.collectAsStateWithLifecycle()

                if (serviceState is ZEMTMyTalentsServiceUiState.Success) {
                    ZEMTMyTalentsScreen(
                        uiState = (serviceState as ZEMTMyTalentsServiceUiState.Success).uiState,
                        onShowDetailDialog = {
                            val detailDialog = ZEMTTalentDetailFragment.newInstance(it)
                            detailDialog.show(
                                childFragmentManager,
                                ZEMTTalentDetailFragment.TAG
                            )
                        }
                    )
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateAppBar()
        collectUiState()
        viewModel.getMyTalents()
    }

    private fun updateAppBar() {
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle("WIP Final talents")
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.serviceState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    ZEMTMyTalentsServiceUiState.Loading -> showLoader()

                    ZEMTMyTalentsServiceUiState.Error -> {
                        hideLoader()
                    }

                    is ZEMTMyTalentsServiceUiState.Success -> {
                        hideLoader()
                    }

                    else -> Unit
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
}