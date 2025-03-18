package com.upax.zemytalents.ui.conversations.qr.data

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

class ZEMTColorPainter(private val color: Color, private val painter: Painter) : Painter() {
    override val intrinsicSize: Size
        get() = painter.intrinsicSize

    override fun DrawScope.onDraw() {
        with(painter) {
            draw(size = size, alpha = 1f, colorFilter = ColorFilter.tint(color))
        }
    }
}