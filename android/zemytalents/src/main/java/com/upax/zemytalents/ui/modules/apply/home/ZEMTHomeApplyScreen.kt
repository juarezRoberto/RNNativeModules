package com.upax.zemytalents.ui.modules.apply.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.modulelist.ZEMTModuleList
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.delay
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTHomeApplyScreen(
    modifier: Modifier = Modifier,
    modules: List<ZEMTModuleUiModel>,
    onNavigateTo: () -> Unit,
) {
    var isAnimationFinished by rememberSaveable { mutableStateOf(false) }
    var isFirstDescriptionShowed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1_000)
        isFirstDescriptionShowed = true
        delay(1_000)
        isAnimationFinished = true
    }

    Box(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_introduction))
            LottieAnimation(
                composition = lottie,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            ZEMTText(
                text = stringResource(R.string.zemt_is_time_to_apply_your_talents),
                textAlign = TextAlign.Center,
                style = RDS.style.TextAppearance_ZCDSApp_Header03,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxlarge))
            )
            ZEMTText(
                text = stringResource(
                    if (isFirstDescriptionShowed) R.string.zemt_home_apply_description_2
                    else R.string.zemt_home_apply_description_1
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
            )
            Spacer(modifier = Modifier.weight(1f, true))
            ZEMTModuleList(
                moduleList = modules,
                discoverModuleProgress = 1f,
                navigateToModule = {
                    if (isAnimationFinished) {
                        onNavigateTo()
                    }
                },
                scrollTarget = ZEMTModuleStage.APPLY,
                scrollPrevious = ZEMTModuleStage.APPLY,
                modifier = Modifier.padding(bottom = 60.dp)
            )
            ZEMTButton(
                text = stringResource(R.string.zemt_apply_talents),
                onClick = { onNavigateTo() },
                enabled = isAnimationFinished,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
                    .padding(bottom = dimensionResource(RDS.dimen.zcds_margin_padding_size_large))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTHomeApplyScreenPreview() {
    ZEMTHomeApplyScreen(
        modules = ZEMTMockModulesData.getModules(),
        onNavigateTo = {}
    )
}