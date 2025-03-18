package com.upax.zemytalents.ui.mytalents.preview

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import com.upax.zemytalents.ui.mytalents.ZEMTMyTalentsViewModel
import com.upax.zemytalents.ui.mytalents.ZEMTPreviewMyTalentsScreen
import kotlinx.coroutines.launch

class ZEMTPreviewMyTalentsFragment : Fragment() {

    private val args: ZEMTPreviewMyTalentsFragmentArgs by navArgs()

    private val viewModel: ZEMTMyTalentsViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.FINAL_TALENTS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finalTalents = Gson().fromJson(args.finalTalents, Array<ZEMTTalent>::class.java)
            .toList()

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val modules by viewModel.modules.collectAsStateWithLifecycle()
                val user by viewModel.user.collectAsStateWithLifecycle()

                if (user != null) {
                    ZEMTPreviewMyTalentsScreen(
                        user = user!!,
                        modules = modules,
                        finalTalents = finalTalents,
                        onFinishedTalentsAnimation = { navigateToFinalTalentScreen() }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle(title = getString(R.string.zemt_my_talents))
    }

    private fun navigateToFinalTalentScreen() = lifecycleScope.launch {
        findNavController().navigate(
            ZEMTPreviewMyTalentsFragmentDirections
                .actionZEMTPreviewMyTalentsFragmentToZEMTTalentsResumeFragment(
                    collaboratorName = viewModel.user.value?.name.orEmpty(),
                    collaboratorId = viewModel.user.value?.zeusId.orEmpty(),
                    collaboratorProfileImageUrl = viewModel.user.value?.photo.orEmpty(),
                    viewType = ZEMTTalentsResumeType.MY_TALENTS
                )
        )
    }

}