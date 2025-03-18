package com.upax.zemytalents.ui.conversations.qr.data

import androidx.compose.ui.graphics.Brush
import io.github.alexzhirkevich.qrose.options.Neighbors
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrBrushMode

class ZEMTBrushColor(
    private val builder: (size: Float) -> Brush
) : QrBrush {

    override val mode: QrBrushMode
        get() = QrBrushMode.Separate

    override fun brush(size: Float, neighbors: Neighbors): Brush = this.builder(size)
}