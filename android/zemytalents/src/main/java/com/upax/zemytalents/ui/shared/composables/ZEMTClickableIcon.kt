package com.upax.zemytalents.ui.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalentAnimationData
import com.upax.zemytalents.ui.shared.composables.talentsprofile.NO_TALENT
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTClickableIcon(
    enableClick: Boolean,
    talentAnimationData: ZEMTTalentAnimationData,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
    shadowElevationDp: Dp = 1.dp,
    iconSizeDp: Dp = 40.dp,
    miniIconSize: Dp = 18.dp,
    iconShape: Shape = CircleShape
) {

    val isComplete = talentAnimationData.talentId != NO_TALENT
    val iterationSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        val backgroundColor =
            if (!talentAnimationData.isTemp) context.getColor(DesignSystem.color.zcds_very_light_gray_200)
            else if (isComplete) context.getColor(DesignSystem.color.zcds_very_light_gray_200) //Confirm module
            else context.getColor(DesignSystem.color.zcds_mid_gray_500) //Discover module

        val borderColor =
            if (!talentAnimationData.isTemp) ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.Dark)
            else if (isComplete) context.getColor(DesignSystem.color.zcds_light_gray_300) //Confirm module
            else context.getColor(DesignSystem.color.zcds_dark_gray_600) //Discover module

        val borderWidth =
            if (!talentAnimationData.isTemp) 1.5.dp
            else if (isComplete) 3.dp //Confirm module
            else 1.5.dp //Discover module

        val confirmTalent = talentAnimationData.talentId != NO_TALENT

        val iconTintColor =
            if (!talentAnimationData.isTemp)
                Color(ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.VeryDark))
            else if (isComplete)
                colorResource(DesignSystem.color.zcds_mid_gray_500) //Confirm module
            else
                colorResource(DesignSystem.color.zcds_white) //Discover module

        Icon(
            painter = painterResource(id = talentAnimationData.icon),
            contentDescription = null,
            modifier = Modifier
                .then(
                    if (enableClick) Modifier.clickable(onClick = {
                        onClick(
                            talentAnimationData.talentId
                        )
                    }, indication = null, interactionSource = iterationSource) else Modifier
                )
                .size(iconSizeDp)
                .graphicsLayer {
                    shape = iconShape
                    clip = true
                    shadowElevation = shadowElevationDp.toPx()
                }
                .background(
                    color = Color(backgroundColor),
                    shape = iconShape
                )
                .border(width = borderWidth, color = Color(borderColor), shape = iconShape)
                .padding(8.dp),
            tint = iconTintColor
        )

        if (talentAnimationData.isTemp && confirmTalent)
            Box(
                modifier = Modifier
                    .then(
                        if (enableClick) Modifier.clickable(
                            onClick = { onClick(talentAnimationData.talentId) },
                            indication = null,
                            interactionSource = iterationSource
                        ) else Modifier
                    )
                    .padding(top = miniIconSize.times(2.3f))
                    .align(Alignment.Center)
                    .background(
                        color = colorResource(DesignSystem.color.zcds_dark_gray_600),
                        shape = iconShape
                    )
                    .size(miniIconSize)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.zemt_ic_padlock_closed_outlined),
                    contentDescription = null,
                    tint = colorResource(DesignSystem.color.zcds_white),
                    modifier = Modifier
                        .size(13.dp)
                        .align(Alignment.Center),
                )
            }
        else if (!talentAnimationData.isTemp && talentAnimationData.talentName.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .then(
                        if (enableClick) Modifier.clickable(
                            onClick = { onClick(talentAnimationData.talentId) },
                            indication = null,
                            interactionSource = iterationSource
                        ) else Modifier
                    )
                    .offset(y = iconSizeDp.times(0.5f))
                    .align(Alignment.Center)
                    .background(
                        color = Color(ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.Dark)),
                        shape = iconShape
                    )
                    .padding(vertical = 2.dp, horizontal = 6.dp)
            ) {
                ZEMTText(
                    text = talentAnimationData.talentName,
                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXXSmall,
                    color = LocalContext.current.getColor(DesignSystem.color.zcds_white),
                    fontSize = 8.sp
                )
            }

            val xOffset: Dp = when (talentAnimationData.order) {
                5, 3 -> 3.dp
                4, 2 -> (-3).dp
                else -> 0.dp
            }

            val paddingStart = when (talentAnimationData.order) {
                4 -> iconSizeDp * 1.04f
                2 -> iconSizeDp * 0.85f
                else -> 0.dp
            }

            val paddingEnd = when (talentAnimationData.order) {
                5 -> iconSizeDp * 1.04f
                3 -> iconSizeDp * 0.85f
                else -> 0.dp
            }

            val labelHeight = 14.dp
            if (talentAnimationData.order != 1) Box(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd)
                    .width(iconSizeDp / 1.6f)
                    .offset(y = iconSizeDp.times(0.5f) - labelHeight, x = xOffset)
                    .align(Alignment.Center)
                    .zIndex(-1f)
                    .background(
                        color = Color(ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.Dark)),
                        shape = iconShape
                    )
                    .padding(vertical = 2.dp)


            ) {
                ZEMTText(
                    text = talentAnimationData.order.toString(),
                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXXSmall,
                    color = LocalContext.current.getColor(DesignSystem.color.zcds_white),
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(
                            when (talentAnimationData.order) {
                                5, 3 -> Alignment.CenterStart
                                4, 2 -> Alignment.CenterEnd
                                else -> Alignment.Center
                            }
                        ),
                )
            } else Image(
                painter = painterResource(R.drawable.zemt_ic_kid_star_filled),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .offset(y = (iconSizeDp / 2) + 36.dp / 2.6f)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ZMTClickableIconPreview() {
    val modifier = Modifier.size(100.dp)
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ZEMTClickableIcon(
            enableClick = false,
            talentAnimationData = ZEMTTalentAnimationData(
                talentId = 1,
                icon = R.drawable.zemt_ic_talent_comunicador,
                isTemp = true,
                order = 1,
                talentName = ""
            ),
            modifier = modifier
        )
        ZEMTClickableIcon(
            enableClick = true,
            talentAnimationData = ZEMTTalentAnimationData(
                talentId = 1,
                icon = R.drawable.zemt_ic_talent_comunicador,
                isTemp = false,
                order = 1,
                talentName = "Reconstructor"
            ),
            modifier = modifier
        )
        ZEMTClickableIcon(
            enableClick = true,
            talentAnimationData = ZEMTTalentAnimationData(
                talentId = 1,
                icon = R.drawable.zemt_ic_talent_comunicador,
                isTemp = false,
                order = 3,
                talentName = "Reconstructor"
            ),
            modifier = modifier
        )
        ZEMTClickableIcon(
            enableClick = false,
            talentAnimationData = ZEMTTalentAnimationData(
                talentId = 1,
                icon = R.drawable.zemt_ic_talent_comunicador,
                isTemp = false,
                order = 4,
                talentName = "Conquistador"
            ),
            modifier = modifier
        )

        ZEMTClickableIcon(
            enableClick = false,
            talentAnimationData = ZEMTTalentAnimationData(
                talentId = NO_TALENT,
                icon = R.drawable.zemt_ic_talent_comunicador,
                isTemp = false,
                order = 2,
                talentName = ""
            ),
            modifier = modifier
        )
    }
}