package com.upax.zemytalents.ui.conversations.makeconversation.composables

import android.text.InputFilter
import android.view.Gravity
import android.widget.EditText
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    supportingText: @Composable (() -> Unit)? = null,
    characterLimit: Int = 100,
    backgroundColor: Color = colorResource(DesignSystem.color.zcds_extra_light_gray_100),
    borderColor: Color = colorResource(DesignSystem.color.zcds_light_gray_300),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (value.isNotEmpty()) borderColor else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(136.dp)
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp),
        ) {
            ZEMTText(
                text = label,
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                color = colorResource(DesignSystem.color.zcds_dark_gray_600).toArgb(),
            )
            var selection by remember { mutableIntStateOf(0) }
            AndroidView(
                factory = { context ->
                    val materialContext =
                        ContextThemeWrapper(context, DesignSystem.style.Theme_ZCDSAppTheme)
                    EditText(materialContext).apply {
                        background = null
                        gravity = Gravity.START
                        setPadding(0)
                        filters = arrayOf(InputFilter.LengthFilter(characterLimit))
                        doOnTextChanged { text, _, _, _ ->
                            if (text.toString().isNotEmpty()) {
                                var offset = 0
                                val newText = buildString {
                                    val lines = text.toString().lines()
                                    lines.forEachIndexed { index, line ->
                                        if (!line.startsWith('•') && line.isNotEmpty()) {
                                            append("• ")
                                            offset += 2
                                        }
                                        append(line)
                                        if (index != lines.lastIndex) append("\n")
                                    }
                                }
                                if (selectionEnd != 0) {
                                    selection = selectionEnd + offset
                                }
                                onValueChange(newText)
                            }
                        }
                    }
                }, modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, start = 12.dp),
                update = { et ->
                    et.setText(value)
                    et.setSelection(selection)
                }
            )
        }

        if (supportingText != null) {
            supportingText()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTTextAreaPreview2() {
    var text by remember { mutableStateOf("Hola") }
    ZEMTTextArea(
        value = text,
        onValueChange = {
            text = it
        },
        label = "Enter comments",
        modifier = Modifier.padding(16.dp),
        supportingText = {
            val textColor = colorResource(DesignSystem.color.zcds_mid_gray_500).toArgb()
            val style = DesignSystem.style.TextAppearance_ZCDSApp_BodySmall
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                ZEMTText(
                    text = stringResource(R.string.zemt_conversations_characters_limit),
                    modifier = Modifier
                        .weight(1f, true),
                    color = textColor,
                    style = style
                )
                ZEMTText(
                    text = "${text.length}/20",
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor,
                    style = style
                )
            }
        },
        characterLimit = 20,
    )
}


@Preview(showBackground = true)
@Composable
private fun ZEMTTextAreaPreview() {
    ZEMTTextArea(
        value = "Sample text",
        onValueChange = {},
        label = stringResource(R.string.zemt_conversations_mandatory_comments),
        modifier = Modifier.padding(16.dp),
        supportingText = {
            val textColor = colorResource(DesignSystem.color.zcds_mid_gray_500).toArgb()
            val style = DesignSystem.style.TextAppearance_ZCDSApp_BodySmall
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .padding(horizontal = 4.dp),
            ) {
                ZEMTText(
                    text = "Character limit reached",
                    modifier = Modifier
                        .weight(1f, true),
                    color = textColor,
                    style = style
                )
                ZEMTText(
                    text = "10/50",
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor,
                    style = style
                )
            }
        },
        characterLimit = 50
    )
}
