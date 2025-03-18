package com.upax.zemytalents.ui.conversations.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.conversations.onboarding.introduction.ZEMTOnboardingTextStep
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zcdesignsystem.R as DesignSystem

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ZEMTConversationOnboardingView(
    slide1Text: String,
    slide2Text: String,
    slide3Text: String,
    nextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pageCount = ZEMTOnboardingSteps.entries.size
    val pagerState = rememberPagerState { pageCount }
    val currentStep: ZEMTOnboardingSteps = ZEMTOnboardingSteps.indexToStep(pagerState.currentPage)

    val lottie by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            when (currentStep) {
                ZEMTOnboardingSteps.INTRODUCTION -> R.raw.zemt_welcome
                ZEMTOnboardingSteps.CONVERSATION -> R.raw.zemt_globe_chat
                ZEMTOnboardingSteps.QR -> R.raw.zemt_qr_code_scanning
            }
        )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = lottie,
            modifier = Modifier.size(180.dp),
            iterations = LottieConstants.IterateForever
        )
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f, true),
            verticalAlignment = Alignment.Top
        ) { page ->
            var title = ""
            var message = ""
            var messageAlign = TextAlign.Left
            when (ZEMTOnboardingSteps.indexToStep(page)) {
                ZEMTOnboardingSteps.INTRODUCTION -> {
                    title = stringResource(R.string.zemt_conversations_onboarding_title)
                    message = slide1Text
                    messageAlign = TextAlign.Center
                }

                ZEMTOnboardingSteps.CONVERSATION -> {
                    title = stringResource(R.string.zemt_conversations_conversation_title)
                    message = slide2Text
                }

                ZEMTOnboardingSteps.QR -> {
                    title = stringResource(R.string.zemt_conversations_qr_title)
                    message = slide3Text
                }
            }
            ZEMTOnboardingTextStep(
                title = title,
                message = message,
                messageAlign = messageAlign
            )
        }
        Row(modifier = Modifier.padding(bottom = 32.dp)) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                val iconSize = 12.dp
                val iconSelectedWidth = 32.dp
                val dynamicWidth = if (isSelected) iconSelectedWidth else iconSize
                val iconWidth by animateDpAsState(
                    targetValue = dynamicWidth,
                    label = "icon width"
                )
                val color =
                    if (isSelected) Color(ZCDSColorUtils.getPrimaryColor()) else colorResource(
                        DesignSystem.color.zcds_light_gray_300
                    )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(iconSize)
                        .width(iconWidth)
                )
            }
        }

        AnimatedVisibility(visible = currentStep == ZEMTOnboardingSteps.QR) {
            ZEMTButton(
                text = stringResource(R.string.zemt_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onClick = nextScreen
            )
        }
    }
}


private enum class ZEMTOnboardingSteps {
    INTRODUCTION,
    CONVERSATION,
    QR;

    fun stepToIndex(): Int {
        return when (this) {
            INTRODUCTION -> 0
            CONVERSATION -> 1
            QR -> 2
        }
    }

    companion object {
        fun indexToStep(index: Int): ZEMTOnboardingSteps {
            return when (index) {
                0 -> INTRODUCTION
                1 -> CONVERSATION
                2 -> QR
                else -> throw IllegalArgumentException("Invalid step index")
            }
        }
    }

}

@Preview
@Composable
private fun ZEMTConversationOnboardingViewPreview() {
    ZEMTConversationOnboardingView(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(16.dp),
        nextScreen = {},
        slide1Text = stringResource(R.string.zemt_conversations_onboarding_message),
        slide2Text = stringResource(R.string.zemt_conversations_conversation_message),
        slide3Text = stringResource(R.string.zemt_conversations_qr_message)
    )
}