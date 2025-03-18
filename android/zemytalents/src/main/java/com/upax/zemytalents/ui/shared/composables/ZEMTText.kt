package com.upax.zemytalents.ui.shared.composables

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTText(
    text: CharSequence,
    modifier: Modifier = Modifier,
    style: Int = RDS.style.TextAppearance_ZCDSApp_BodyMedium,
    @ColorInt color: Int = LocalContext.current.getColor(RDS.color.zcds_very_dark_gray_700),
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit? = null,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: Int = Typeface.NORMAL
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { textView ->
            textView.text = text
            textView.setTextAppearance(style)
            textView.setTextColor(color)
            textView.gravity = when (textAlign) {
                TextAlign.Start -> Gravity.START
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.NO_GRAVITY
            }
            textView.setTypeface(textView.typeface, textStyle)
            fontSize?.let { textView.textSize = fontSize.value }
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.maxLines = maxLines
        }
    )
}

@Composable
internal fun ZEMTText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: Int = RDS.style.TextAppearance_ZCDSApp_BodyMedium,
    @ColorInt color: Int = LocalContext.current.getColor(RDS.color.zcds_very_dark_gray_700),
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit? = null,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: Int = Typeface.NORMAL
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { textView ->
            textView.text = text.toSpannableString()
            textView.setTextAppearance(style)
            textView.setTextColor(color)
            textView.gravity = when (textAlign) {
                TextAlign.Start -> Gravity.START
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.NO_GRAVITY
            }
            textView.setTypeface(textView.typeface, textStyle)
            fontSize?.let { textView.textSize = fontSize.value }
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.maxLines = maxLines
        }
    )
}

private fun AnnotatedString.toSpannableString(): SpannableString {
    val spannableString = SpannableString(this.text)
    this.spanStyles.forEach { spanRange ->
        val spanStyle = spanRange.item
        val start = spanRange.start
        val end = spanRange.end

        val flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

        spanStyle.fontWeight?.let { fontWeight ->
            val typefaceStyle = when (fontWeight) {
                FontWeight.Bold -> Typeface.BOLD
                FontWeight.Normal -> Typeface.NORMAL
                else -> Typeface.NORMAL
            }
            spannableString.setSpan(StyleSpan(typefaceStyle), start, end, flags)
        }

        spanStyle.fontStyle?.let { fontStyle ->
            if (fontStyle == FontStyle.Italic) {
                spannableString.setSpan(StyleSpan(Typeface.ITALIC), start, end, flags)
            }
        }
    }
    return spannableString
}

@Preview(showBackground = true)
@Composable
private fun ZEMTButtonPreview() {
    ZEMTText(text = "Continuar")
}