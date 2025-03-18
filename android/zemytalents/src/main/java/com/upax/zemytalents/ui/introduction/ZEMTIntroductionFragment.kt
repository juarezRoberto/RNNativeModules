package com.upax.zemytalents.ui.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.introduction.screen.ZEMTWelcomeScreen

internal class ZEMTIntroductionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as ZEMTHostActivity).updateAppBarTitle(getString(R.string.zemt_my_talents))
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTWelcomeScreen(
                    onContinue = ::navigateToAdviceScreen,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }

    private fun navigateToAdviceScreen() {
        findNavController().navigate(
            ZEMTIntroductionFragmentDirections.actionZEMTIntroductionFragmentToZEMTAdviceFragment()
        )
    }
}

@Preview
@Composable
private fun ZEMTIntroductionPreview() {
    ZEMTWelcomeScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        onContinue = {})
}