package com.upax.zemytalents.ui.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

class ZEMTConversationSavedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? ZEMTHostActivity)?.hideAppBar()
        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTConversationSavedView(
                    modifier = Modifier.systemBarsPadding(),
                    onExit = { findNavController().navigateUp() }
                )
            }
        }
    }
}

@Composable
fun ZEMTConversationSavedView(modifier: Modifier = Modifier, onExit: () -> Unit = {}) {
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_confetti))
    val lottieCup by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_cup))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxHeight(.5f)) {
            LottieAnimation(
                composition = lottie,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )
            LottieAnimation(
                composition = lottieCup,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.fillMaxSize()
            )
        }
        ZEMTText(
            text = stringResource(R.string.zemt_congratulations),
            style = DesignSystem.style.TextAppearance_ZCDSApp_Header03,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        ZEMTText(
            stringResource(R.string.zemt_conversationFinished),
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f, true))
        ZEMTButton(
            text = stringResource(R.string.zemt_tip_exit),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            onClick = onExit
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTConversationSavedViewPreview() {
    ZEMTConversationSavedView(modifier = Modifier.fillMaxWidth())
}