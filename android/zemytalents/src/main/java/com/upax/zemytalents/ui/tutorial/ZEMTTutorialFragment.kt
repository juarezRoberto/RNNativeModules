package com.upax.zemytalents.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.ZEMTHostActivity

class ZEMTTutorialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTTutorialScreen(
                    onTutorialFinished = { navigateToHomeScreen() }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ZEMTHostActivity).updateAppBarTitle(getString(R.string.zemt_tutorial))
        Toast.makeText(requireContext(), "wait to finish animation", Toast.LENGTH_LONG).show()
    }

    private fun navigateToHomeScreen() {
    }
}