package com.upax.zemytalents.ui.introduction.screen

import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
fun ZEMTWelcomeScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.zemt_welcome_title),
    message: String = stringResource(R.string.zemt_welcome_message),
    incentiveMessage: String = stringResource(R.string.zemt_welcome_incentive_message)
) {
    Column(modifier = modifier.systemBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
        val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_introduction))
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        ZEMTText(
            text = title,
            style = RDS.style.TextAppearance_ZCDSApp_Header03,
            modifier = Modifier.padding(top = 42.dp)
        )
        ZEMTText(
            text = message,
            style = RDS.style.TextAppearance_ZCDSApp_BodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 32.dp)
        )
        ZEMTText(
            text = incentiveMessage,
            style = RDS.style.TextAppearance_ZCDSApp_BodyMediumBlack,
            textAlign = TextAlign.Center,
            textStyle = Typeface.ITALIC,
            modifier = Modifier.padding(top = 32.dp),
        )
        Spacer(modifier = Modifier.weight(1f, true))
        ZEMTButton(
            text = stringResource(R.string.zemt_continue),
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
private fun ZEMTWelcomeScreenPreview() {
    ZEMTWelcomeScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        onContinue = {}
    )
}