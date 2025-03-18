package com.upax.zemytalents.ui.shared.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zemytalents.common.ZEMTCanvasExtension.drawVerticalLine
import com.upax.zemytalents.domain.models.ZEMTDropDownDirection
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTDropdownGroup(
    groupTitle: String,
    @DrawableRes groupIcon: Int,
    modifier: Modifier = Modifier,
    endIcon: Int = DesignSystem.drawable.zcds_ic_right_arrow,
    endIconTint: Color  = LocalContentColor.current,
    endIconSize: Dp = 12.dp,
    isOpenable: Boolean = true,
    isOpen: Boolean = false,
    closedIconDirection: ZEMTDropDownDirection = ZEMTDropDownDirection.RIGHT,
    openedIconDirection: ZEMTDropDownDirection = ZEMTDropDownDirection.DOWN,
    onGroupClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    var openState by rememberSaveable { mutableStateOf(isOpen) }
    val backgroundColor = colorResource(id = DesignSystem.color.zcds_extra_light_gray_100)
    val lineColor = Color(ZCDSColorUtils.getPrimaryColor())
    val strokeWidth = 8.dp
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .clickable {
                if (isOpenable) {
                    openState = !openState
                }
                onGroupClick()

            }
            .animateContentSize()
    ) {
        val verticalColumnPadding = 12.dp
        val horizontalColumnPadding = 16.dp
        Column(modifier = Modifier.drawBehind {
            drawVerticalLine(color = lineColor, strokeWidth = strokeWidth.toPx())
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = verticalColumnPadding)
                    .padding(horizontal = horizontalColumnPadding)
            ) {
                val iconPadding =
                    if (closedIconDirection == ZEMTDropDownDirection.UP) horizontalColumnPadding - strokeWidth + 8.dp else horizontalColumnPadding - strokeWidth
                Icon(
                    painterResource(id = groupIcon),
                    contentDescription = null,
                    modifier = Modifier.padding(start = iconPadding)
                )

                ZEMTText(
                    text = groupTitle,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(horizontal = 16.dp),
                    style = if (!openState) DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium else DesignSystem.style.TextAppearance_ZCDSApp_Header05,
                )


                Icon(
                    painter = painterResource(id = endIcon),
                    contentDescription = null,
                    tint = endIconTint,
                    modifier = Modifier.size(endIconSize).rotate(if (openState) openedIconDirection.rotation else closedIconDirection.rotation)
                )
            }
            AnimatedVisibility(visible = openState) {
                content(PaddingValues(horizontal = strokeWidth.div(2)))
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xfffabc,
    showSystemUi = true,
    device = Devices.PIXEL_5
)
@Composable
private fun ZEMTDropdownGroupPreview() {
    val title = "Conversaciones"
    val icon = DesignSystem.drawable.zcds_ic_per_capacitaciones
    Column {
        ZEMTDropdownGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isOpen = false,
            groupTitle = title,
            groupIcon = icon,
            closedIconDirection = ZEMTDropDownDirection.UP
        ) {

            Column(Modifier.padding(it)) {
                ZEMTText(text = "Text")
                ZEMTText(text = "Text")
                ZEMTText(text = "Text")
                ZEMTText(text = "Text")
            }

        }

        ZEMTDropdownGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isOpenable = false,
            groupTitle = title,
            groupIcon = icon
        )
    }
}

