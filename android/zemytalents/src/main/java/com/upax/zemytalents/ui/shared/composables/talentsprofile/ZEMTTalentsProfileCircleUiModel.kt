package com.upax.zemytalents.ui.shared.composables.talentsprofile

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class ZEMTTalentsProfileCircleUiModel(
    val color: Color = Color.Green,
    val radius: Dp = 0.dp,
    val borderWidth: Dp = 0.dp,
    val borderColor: Color = Color.Transparent
)