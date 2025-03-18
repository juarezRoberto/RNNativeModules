package com.upax.zemytalents.ui.conversations.makeconversation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.data.mock.ZEMTMockYesNoList
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun <T> ZEMTFormSpinner(
    hint: String,
    optionList: List<ZEMTFormSpinnerOption<T>>,
    onOptionSelected: (ZEMTFormSpinnerOption<T>) -> Unit,
    modifier: Modifier = Modifier
) {
    var isOpen by remember { mutableStateOf(false) }
    var selectedOption: ZEMTFormSpinnerOption<T>? by remember { mutableStateOf(optionList.firstOrNull { it.selected }) }
    val icon =
        if (isOpen) DesignSystem.drawable.zcds_ic_chevron_up_outlined else DesignSystem.drawable.zcds_ic_chevron_down_outlined
    val textStyle =
        if (selectedOption == null && !isOpen) DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge else if (isOpen) DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium else DesignSystem.style.TextAppearance_ZCDSApp_BodySmall
    val textColor = colorResource(DesignSystem.color.zcds_dark_gray_600)
    val defaultModifier =
        Modifier
            .then(
                if (selectedOption != null) Modifier.border(
                    width = 1.dp,
                    color = colorResource(DesignSystem.color.zcds_mid_gray_500),
                    shape = CardDefaults.shape
                ) else Modifier
            )
    ZEMTRoundedBox(
        modifier = modifier
            .then(defaultModifier)
            .clickable {
                isOpen = !isOpen
            },
        verticalPadding = 16.dp,
        horizontalPadding = 12.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ZEMTText(
                text = hint,
                style = textStyle,
                modifier = Modifier.weight(1f, true),
                color = textColor.toArgb(),
            )
            Image(
                painter = painterResource(icon),
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = isOpen) {
            Column(modifier = Modifier.padding(start = 20.dp)) {
                optionList.forEach { option ->
                    ZEMTText(
                        text = option.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = option
                                isOpen = false
                                onOptionSelected(option)
                            }
                            .padding(vertical = 12.dp),
                        style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge
                    )
                }
            }
        }

        AnimatedVisibility(visible = !isOpen && selectedOption != null) {
            ZEMTText(
                text = selectedOption?.text.orEmpty(),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge
            )
        }
    }
}

internal data class ZEMTFormSpinnerOption<T>(
    val id: String,
    val text: String,
    val value: T,
    val selected: Boolean
)


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ZEMTFormSpinnerPreview() {
    ZEMTFormSpinner(
        hint = stringResource(R.string.zemt_conversations_make_conversation_is_done),
        optionList = ZEMTMockYesNoList().map { it.copy(selected = true) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onOptionSelected = {}
    )
}