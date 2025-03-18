package com.upax.zemytalents.ui.mytalents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTProfileTalentState
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.modulelist.ZEMTModuleList
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfile
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileStages
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileUserUiModel
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTPreviewMyTalentsScreen(
    modifier: Modifier = Modifier,
    user: ZCSIUser,
    modules: List<ZEMTModuleUiModel>,
    finalTalents: List<ZEMTTalent>,
    onFinishedTalentsAnimation: () -> Unit
) {
    val congratsLottie by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.zemt_animation_trophy)
    )
    var isAnimationFinished by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var stage by remember {
        mutableStateOf<ZEMTTalentsProfileStages>(ZEMTTalentsProfileStages.Apply(finalTalents))
    }

    val onFinishedTalents = remember {
        {
            coroutineScope.launch {
                stage = ZEMTTalentsProfileStages.Complete(finalTalents)
                delay(1500L)
                onFinishedTalentsAnimation.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(3_000)
        isAnimationFinished = true
    }

    Box(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        if (isAnimationFinished) {
            Column {
                ZEMTTalentsProfile(
                    stage = stage,
                    data = ZEMTTalentsProfileUserUiModel(
                        userName = user.getFullName(),
                        userProfileUrl = user.photo
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_xxlarge))
                        .height(267.dp),
                    onTalentClick = {
                    },
                    onTalentFocusListener = { talentState ->
                        when (talentState) {
                            is ZEMTProfileTalentState.Label -> {
                                if (talentState.isTheLastTalent) onFinishedTalents.invoke()
                            }
                            else -> Unit
                        }
                    }
                )
                ZEMTText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large)),
                    text = "¡Excelente trabajo!",
                    style = RDS.style.TextAppearance_ZCDSApp_Header03,
                    textAlign = TextAlign.Center
                )
                ZEMTText(
                    modifier = Modifier.padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large)),
                    text = stringResource(R.string.zemt_finished_apply_talents_description_1, finalTalents.size),
                    textAlign = TextAlign.Center
                )
                ZEMTText(
                    modifier = Modifier.padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large)),
                    text = stringResource(R.string.zemt_finished_apply_talents_description_2),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = congratsLottie,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(296.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ZEMTText(
                        stringResource(R.string.zemt_excellent_job),
                        style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_Header03,
                    )
                    ZEMTText(
                        modifier = Modifier.padding(top = dimensionResource(RDS.dimen.zcds_margin_padding_size_large)),
                        text = stringResource(
                            R.string.zemt_talent_final_talents_description,
                            finalTalents.size
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f, true))
                ZEMTModuleList(
                    moduleList = modules,
                    discoverModuleProgress = 1f,
                    navigateToModule = {

                    },
                    scrollTarget = ZEMTModuleStage.APPLY,
                    scrollPrevious = ZEMTModuleStage.APPLY,
                    modifier = Modifier.padding(bottom = 60.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTPreviewMyTalentsScreenPreview() {
    ZEMTPreviewMyTalentsScreen(
        user = ZCSIUser(name = "Robert"),
        modules = ZEMTMockModulesData.getModules(),
        finalTalents = ZEMTMockModulesData.getTalents(),
        onFinishedTalentsAnimation = {}
    )
}