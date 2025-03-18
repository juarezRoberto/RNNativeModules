package com.upax.zemytalents.ui.modules.discover.survey.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class ZEMTArcShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val newSize = Size(size.width * 2, size.width * 2)
        val circleSize = 42.dp.toPx(density)

        val path = Path().apply {
            val rect = Rect(offset = Offset(-(size.width / 2), circleSize / 2), size = newSize)
            addArc(
                oval = rect,
                startAngleDegrees = 0f,
                sweepAngleDegrees = 360f,
            )
        }
        return Outline.Generic(path)
    }

    private fun Dp.toPx(density: Density): Float {
        return value * density.density
    }
}