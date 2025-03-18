package com.upax.zemytalents.ui.modules.discover.survey.composables.splitlayout

import androidx.compose.ui.Alignment as ComposeAlignment
import androidx.compose.ui.graphics.Color

internal data class ZEMTSplitLayoutBackgroundContent(
    val color: Color, val alignment: Alignment
) {

    enum class Alignment {
        LEFT, RIGHT, FULL
    }

    val composeAlignment: androidx.compose.ui.Alignment
        get() =
            when (alignment) {
                Alignment.LEFT -> ComposeAlignment.CenterStart
                Alignment.RIGHT -> ComposeAlignment.CenterEnd
                Alignment.FULL -> ComposeAlignment.Center
            }

}