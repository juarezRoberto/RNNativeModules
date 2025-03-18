package com.upax.zemytalents.ui.modules.discover.survey.composables.splitlayout

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant

internal class ZEMTSplitLayoutStateHolder(private val context: Context) {

    val backgroundContent = mutableStateOf<ZEMTSplitLayoutBackgroundContent?>(null)

    fun setLeftBackgroundContent(@ColorRes color: Int) = updateBackgroundContent(
        color = color,
        applyLightness = true,
        alignment = ZEMTSplitLayoutBackgroundContent.Alignment.LEFT
    )

    fun setRightBackgroundContent(@ColorRes color: Int) = updateBackgroundContent(
        color = color,
        applyLightness = true,
        alignment = ZEMTSplitLayoutBackgroundContent.Alignment.RIGHT
    )

    fun setFullBackgroundContent(@ColorRes color: Int) = updateBackgroundContent(
        color = color,
        applyLightness = false,
        alignment = ZEMTSplitLayoutBackgroundContent.Alignment.FULL
    )

    fun resetBackgroundContent() {
        backgroundContent.value = null
    }

    private fun updateBackgroundContent(
        @ColorRes color: Int,
        applyLightness: Boolean = true,
        alignment: ZEMTSplitLayoutBackgroundContent.Alignment
    ) {
        var intColor = context.getColor(color)
        if (applyLightness) {
            intColor = ZCDSColorUtils.getColorVariant(intColor, ZCDSColorVariant.ExtraLight)
        }
        backgroundContent.value = ZEMTSplitLayoutBackgroundContent(
            color = Color(intColor),
            alignment = alignment
        )
    }

}

@Composable
internal fun rememberBackgroundColorsStateHolder(
    context: Context = LocalContext.current
): ZEMTSplitLayoutStateHolder {
    return remember { ZEMTSplitLayoutStateHolder(context = context) }
}
