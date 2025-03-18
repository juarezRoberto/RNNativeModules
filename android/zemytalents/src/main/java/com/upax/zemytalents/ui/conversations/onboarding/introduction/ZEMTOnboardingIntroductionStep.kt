package com.upax.zemytalents.ui.conversations.onboarding.introduction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTOnboardingTextStep(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    titleStyle: Int = DesignSystem.style.TextAppearance_ZCDSApp_Header03,
    messageAlign: TextAlign = TextAlign.Left
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ZEMTText(
            text = title,
            style = titleStyle
        )
        ZEMTText(
            text = message,
            style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_BodyMedium,
            modifier = Modifier.padding(top = 32.dp),
            textAlign = messageAlign
        )
    }
}

@Preview
@Composable
private fun ZEMTOnboardingIntroductionStepPreview() {
    ZEMTOnboardingTextStep(
        title = stringResource(R.string.zemt_conversations_onboarding_title),
        message = stringResource(R.string.zemt_conversations_onboarding_message)
    )
}