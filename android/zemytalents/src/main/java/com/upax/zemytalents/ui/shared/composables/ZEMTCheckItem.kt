package com.upax.zemytalents.ui.shared.composables

import androidx.annotation.DrawableRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant
import com.upax.zcdesignsystem.widget.ZCDSRadioButton
import com.upax.zemytalents.R
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTCheckItem(
    itemText: String,
    @DrawableRes itemIcon: Int,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(ZCDSColorUtils.getPrimaryColor()),
    backgroundColor: Color = colorResource(DesignSystem.color.zcds_extra_light_gray_100),
    onItemClick: () -> Unit = {}
) {
    val itemBackgroundColor = if (isChecked) Color(
        ZCDSColorUtils.getColorVariant(
            primaryColor.toArgb(),
            ZCDSColorVariant.ExtraLight
        )
    ).copy(alpha = 0.5f) else backgroundColor
    val paddingLeftText = LocalDensity.current.run { 12.dp.roundToPx() }
    val verticalPadding = LocalDensity.current.run { 16.dp.roundToPx() }
    Row(
        modifier = modifier
            .background(
                color = itemBackgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            factory = { context ->
                val materialContext =
                    ContextThemeWrapper(context, DesignSystem.style.Theme_ZCDSAppTheme)
                ZCDSRadioButton(materialContext).apply {
                    text = itemText
                    setPadding(paddingLeftText, verticalPadding, 0, verticalPadding)
                    isClickable = false
                }
            },
            update = { radioButton ->
                radioButton.isChecked = isChecked
            },
            modifier = Modifier
                .weight(1f, true)
                .padding(start = 8.dp)
        )
        if(itemIcon != -1)Icon(
            painter = painterResource(id = itemIcon),
            contentDescription = itemText,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTCheckItemPreview() {
    Column {
        ZEMTCheckItem(
            isChecked = false,
            itemText = "Fijar objetivos",
            itemIcon = R.drawable.zemt_ic_school,
            modifier = Modifier.padding(8.dp)
        )

        ZEMTCheckItem(
            isChecked = true,
            itemText = "Fijar objetivos",
            itemIcon = R.drawable.zemt_ic_school,
            modifier = Modifier.padding(8.dp)
        )

        ZEMTCheckItem(
            isChecked = true,
            itemText = "Fijar objetivos asdas asdasd asdas dasd as dasd asdasd asd  asd a",
            itemIcon = -1,
            modifier = Modifier.padding(8.dp)
        )
    }
}