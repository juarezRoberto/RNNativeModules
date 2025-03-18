package com.upax.zemytalents.ui.shared.composables.talentsprofile

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class ZEMTTalentsProfileStyleUiModel(
    val shadowElevation: Dp = 4.dp,
    val profilePictureStrokeColor: Color = Color.Red,
    val profilePictureStrokeWidth: Dp = 3.dp,
    @ColorRes val profileLettersColor: Int? = null,
    val innerCircle: ZEMTTalentsProfileCircleUiModel = ZEMTTalentsProfileCircleUiModel(),
    val outerCircle: ZEMTTalentsProfileCircleUiModel = ZEMTTalentsProfileCircleUiModel(),
)