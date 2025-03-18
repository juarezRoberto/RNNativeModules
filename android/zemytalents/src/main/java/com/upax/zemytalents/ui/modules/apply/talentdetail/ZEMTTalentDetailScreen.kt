package com.upax.zemytalents.ui.modules.apply.talentdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText

@Composable
internal fun ZEMTTalentDetailScreen(
    title: String,
    titleDrawableIcon: Int,
    description: String,
    lottieUrl: String,
    onClose: () -> Unit
) {
    val lottie by rememberLottieComposition(LottieCompositionSpec.Url(lottieUrl))
    Box {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Image(
                painter = painterResource(R.drawable.zemt_ic_dialog_drag),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                alignment = Alignment.Center,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(titleDrawableIcon),
                    contentDescription = null,
                    tint = colorResource(com.upax.zcdesignsystem.R.color.zcds_very_dark_gray_700)
                )
                ZEMTText(
                    text = title,
                    style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_Header04,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(start = dimensionResource(com.upax.zcdesignsystem.R.dimen.zcds_margin_padding_size_small))
                )
                Icon(
                    painter = painterResource(com.upax.zcdesignsystem.R.drawable.zcds_ic_x_solid),
                    contentDescription = null,
                    modifier = Modifier.clickable { onClose.invoke() },
                    tint = colorResource(com.upax.zcdesignsystem.R.color.zcds_very_dark_gray_700)
                )
            }

            LottieAnimation(
                composition = lottie,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .heightIn(max = 200.dp)
            )

            ZEMTText(
                text = description,
                style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_BodyMedium,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTTalentDetailScreenPreview() {
    ZEMTTalentDetailScreen(
        title = "Tu talento Confidente",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam aliquet vulputate placerat. Quisque fermentum erat non diam luctus luctus. Vivamus lacinia diam eget aliquet elementum. Proin molestie erat quis lorem lobortis, vel maximus libero fermentum. Etiam nec interdum risus. Fusce auctor fermentum dictum. Pellentesque sapien elit, elementum a mattis eget, consequat quis leo. Quisque pretium cursus orci, eleifend gravida tortor. Nulla facilisi. Sed nibh elit, molestie et sapien et, dapibus efficitur lacus.\n" +
                "Suspendisse in nisl velit. Ut vel magna a arcu pulvinar congue a eget ex. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse potenti. Aenean ut vehicula augue. Morbi ipsum lorem, dictum sed sagittis ut, interdum vel neque. Nam a quam vestibulum, sagittis mauris nec, mollis augue. Nullam eget massa eget augue elementum porttitor. Curabitur pharetra fermentum varius. Ut placerat, ligula vitae feugiat facilisis, tortor risus porta sem, eu auctor lacus nisl et ex. Duis bibendum dignissim lectus, quis vehicula lorem cursus eget. Cras et tellus quis ex ullamcorper viverra. Ut sodales volutpat porttitor. Mauris egestas eros a condimentum volutpat.",
        lottieUrl = "https://files.talentozeus-dev.com.mx/Utilerias/Conversaciones/talentos-lotties/MT+-+Conciliador.json",
        onClose = {},
        titleDrawableIcon = R.drawable.zemt_ic_padlock_closed_filled
    )
}