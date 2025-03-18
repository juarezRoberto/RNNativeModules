package com.upax.zemytalents.ui.conversations.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTDataProvider
import com.upax.zemytalents.di.ZEMTUseCaseProvider
import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType

internal class ZEMTConversationOnboardingFragment : Fragment() {

    private val getCaptions by lazy {
        ZEMTUseCaseProvider.provideGetCaptionTextUseCase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setTitle()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTConversationOnboardingView(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(16.dp),
                    nextScreen = ::goToChooseCollaborator,
                    slide1Text = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_INICIAR_CONVERSACION_SLIDE1),
                    slide2Text = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_INICIAR_CONVERSACION_SLIDE2),
                    slide3Text = getCaptions.invoke(ZEMTCaptionCatalog.FORMADOR_INICIAR_CONVERSACION_SLIDE3)
                )
            }
        }
    }

    private fun setTitle() {
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle(
            title = getString(R.string.zemt_my_talents),
            subtitle = getString(R.string.zemt_conversations_start),
            style = ZCDSTopAppBar.TitlesStyle.Medium
        )
    }

    private fun goToChooseCollaborator() {
        ZEMTDataProvider.provideLocalPreferences(requireContext())
            .makeConversationOnboardingShown = true
        findNavController().navigate(
            ZEMTConversationOnboardingFragmentDirections.goToCollaboratorList(viewType = ZEMTCollaboratorListViewType.MAKE_CONVERSATION)
        )
    }
}