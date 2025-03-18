package com.upax.zemytalents.common

import com.upax.zccommon.extensions.SPACE
import java.util.Locale

internal object ZEMTStringExtensions {
    fun String.capitalizeText(delimiter: String = String.SPACE): String =
        split(delimiter).joinToString(delimiter) { word ->
            word.lowercase().replaceFirstChar { it.titlecase() }
        }
}