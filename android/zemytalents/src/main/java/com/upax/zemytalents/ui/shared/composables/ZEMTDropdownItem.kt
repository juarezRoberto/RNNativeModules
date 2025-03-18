package com.upax.zemytalents.ui.shared.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTDropdownItem(
    @DrawableRes itemIcon: Int,
    itemText: String,
    modifier: Modifier = Modifier,
    dividerColor: Color = DividerDefaults.color,
    onItemClick: () -> Unit = {}
) {
    Column(modifier= Modifier.clickable { onItemClick() }) {
        HorizontalDivider(color = dividerColor)
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = itemIcon), contentDescription = null)
            ZEMTText(
                text = itemText,
                modifier = Modifier.padding(start = 16.dp),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun ZEMTDropdownItemPreview() {
    val icon = DesignSystem.drawable.zcds_ic_per_capacitaciones
    ZEMTDropdownItem(
        itemIcon = icon,
        itemText = "Lorem ipsum dolor sit amet"
    )
}