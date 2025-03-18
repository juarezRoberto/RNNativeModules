package com.upax.zemytalents.ui.modules.confirm.survey.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTCheckboxItem(
    text: String,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit) = {}
) {
    val udnColor = Color(ZCDSColorUtils.getPrimaryColor())
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                uncheckedColor = udnColor,
                checkedColor = udnColor
            ),
            checked = checked,
            enabled = enabled,
            onCheckedChange = onCheckedChange
        )
        ZEMTText(text = text, style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewZEMTCheckboxItem() {
    Column {
        ZEMTCheckboxItem(checked = true, text = "Checkbox item")
        ZEMTCheckboxItem(checked = false, text = "Checkbox item")
        ZEMTCheckboxItem(checked = true, text = "Checkbox item", enabled = false)
        ZEMTCheckboxItem(checked = false, text = "Checkbox item", enabled = false)
    }
}