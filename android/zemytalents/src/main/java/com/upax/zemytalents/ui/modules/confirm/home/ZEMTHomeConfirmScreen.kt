package com.upax.zemytalents.ui.modules.confirm.home

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.upax.zemytalents.domain.models.ZEMTProfileTalentState
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.modules.confirm.home.models.ZEMTHomeConfirmUiState
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.modulelist.ZEMTModuleList
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfile
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileStages
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileUserUiModel
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import kotlinx.coroutines.delay
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTHomeConfirmScreen(
    modifier: Modifier = Modifier,
    uiState: ZEMTHomeConfirmUiState,
    modules: List<ZEMTModuleUiModel>,
    shouldSkipAnimations: Boolean,
    onNavigateToConfirmSurveyScreen: (List<ZEMTTalent>) -> Unit
) {
    val context = LocalContext.current
    var talentText by rememberSaveable { mutableStateOf<String?>(null) }
    var talentDescriptionText by rememberSaveable { mutableStateOf<String?>(null) }
    val congratsLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.zemt_animation_trophy))
    var showTalentsComponent by rememberSaveable { mutableStateOf(shouldSkipAnimations) }
    var isTalentsAnimationFinished by rememberSaveable { mutableStateOf(shouldSkipAnimations) }

    LaunchedEffect(Unit) {
        if (shouldSkipAnimations) {
            talentText = context.getString(R.string.zemt_confirm_start_text)
            talentDescriptionText =
                context.getString(R.string.zemt_confirm_text_description)
        } else {
            delay(2_000)
            showTalentsComponent = true
        }
    }

    Box(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (showTalentsComponent) {
                uiState.user?.let {
                    Column {
                        ZEMTTalentsProfile(
                            stage = if (shouldSkipAnimations) ZEMTTalentsProfileStages.ConfirmNoAnimation(
                                uiState.talents?.dominantTalents.orEmpty()
                            )
                            else ZEMTTalentsProfileStages.Confirm(uiState.talents?.dominantTalents.orEmpty()),
                            data = ZEMTTalentsProfileUserUiModel(
                                userName = uiState.user.getFullName(),
                                userProfileUrl = uiState.user.photo
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(267.dp),
                            onTalentClick = {},
                            onTalentFocusListener = { talentState ->
                                talentText = if (talentState is ZEMTProfileTalentState.Label) {
                                    talentState.talent
                                } else {
                                    null
                                }
                                if (talentState is ZEMTProfileTalentState.Done) {
                                    isTalentsAnimationFinished = true
                                    talentText = context.getString(R.string.zemt_confirm_start_text)
                                    talentDescriptionText =
                                        context.getString(R.string.zemt_confirm_text_description)
                                }
                            }
                        )
                        talentText?.let { text ->
                            ZEMTText(
                                text = text,
                                style = RDS.style.TextAppearance_ZCDSApp_Header03,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = dimensionResource(RDS.dimen.zcds_txt_large))
                            )
                        }
                        talentDescriptionText?.let { text ->
                            ZEMTText(
                                text = text,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large))
                                    .fillMaxWidth()
                                    .padding(top = dimensionResource(RDS.dimen.zcds_txt_large))
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        composition = congratsLottie,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(296.dp)
                    )
                    ZEMTText(
                        stringResource(R.string.zemt_congratulations),
                        style = RDS.style.TextAppearance_ZCDSApp_Header03,
                    )
                    ZEMTText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.zemt_five_talents_discovered)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f, true))
            Column {
                ZEMTModuleList(
                    moduleList = modules,
                    discoverModuleProgress = 1f,
                    navigateToModule = {
                        if (isTalentsAnimationFinished) {
                            onNavigateToConfirmSurveyScreen(uiState.talents?.dominantTalents.orEmpty())
                        }
                    },
                    scrollTarget = ZEMTModuleStage.CONFIRM,
                    scrollPrevious = ZEMTModuleStage.CONFIRM,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                ZEMTButton(
                    text = stringResource(R.string.zemt_start_to_confirm),
                    onClick = { onNavigateToConfirmSurveyScreen(uiState.talents?.dominantTalents.orEmpty()) },
                    enabled = isTalentsAnimationFinished,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTHomeConfirmScreenPreview() {
    ZEMTHomeConfirmScreen(
        uiState = ZEMTHomeConfirmUiState(
            talents = null
        ),
        modules = emptyList(),
        shouldSkipAnimations = false,
        onNavigateToConfirmSurveyScreen = {}
    )
}