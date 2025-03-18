package com.upax.zemytalents.ui.advice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.advice.composables.ZEMTAdviceGrid
import com.upax.zemytalents.ui.advice.model.ZEMTAdviceItemModel
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
fun ZEMTAdviceScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.zemt_advice_title),
    adviceList: List<ZEMTAdviceItemModel> = listOf(
        ZEMTAdviceItemModel(
            resourceId = R.drawable.zemt_ic_wifi,
            description = stringResource(R.string.zemt_advice_wifi)
        ),
        ZEMTAdviceItemModel(
            resourceId = R.drawable.zemt_ic_timer,
            description = stringResource(R.string.zemt_advice_be_patience)
        ),
        ZEMTAdviceItemModel(
            resourceId = R.drawable.zemt_ic_send_to_mobile,
            description = stringResource(R.string.zemt_advice_exit_warning)
        )
    )
) {
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_colibri))
    Column(modifier = modifier.systemBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition = lottie,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .padding(top = 88.dp)
                .size(137.dp)
        )
        ZEMTText(text = title, style = RDS.style.TextAppearance_ZCDSApp_Header03)
        ZEMTAdviceGrid(
            adviceList = adviceList,
            modifier = Modifier
                .padding(top = 64.dp)
                .padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f, true))
        ZEMTButton(
            text = stringResource(R.string.zemt_ok),
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun ZEMTAdviceScreenPreview() {
    ZEMTAdviceScreen(modifier = Modifier.fillMaxSize(), onContinue = {})
}