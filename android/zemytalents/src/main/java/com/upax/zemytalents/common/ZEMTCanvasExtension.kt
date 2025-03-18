package com.upax.zemytalents.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

internal object ZEMTCanvasExtension {
    fun DrawScope.drawVerticalLine(
        color: Color,
        start: Offset = Offset(0f, 0f),
        end: Offset = Offset(0f, size.height),
        strokeWidth: Float = 8.dp.toPx()
    ) {
        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = strokeWidth,
            cap = Stroke.DefaultCap,
            pathEffect = null,
            alpha = 1.0f,
            colorFilter = null,
            blendMode = DrawScope.DefaultBlendMode
        )
    }
}