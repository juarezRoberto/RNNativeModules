package com.upax.zemytalents.ui.conversations.conversationhistory.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTEmptyConversation(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 18.dp).systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val lottie by rememberLottieComposition(LottieCompositionSpec.Asset(ZCDSLottieCatalog.ResourceNotFound.filename))
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(263 / 213f)
                .padding(horizontal = 56.dp)
        )

        ZEMTText(
            text = stringResource(R.string.zemt_conversation_empty_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = DesignSystem.style.TextAppearance_ZCDSApp_Header02
        )
        ZEMTText(
            text = stringResource(R.string.zemt_conversation_empty_message),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            textAlign = TextAlign.Center,
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge
        )

    }
}

@Preview
@Composable
private fun ZEMTEmptyConversationPreview() {
    ZEMTEmptyConversation()
}