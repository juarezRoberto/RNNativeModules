package com.upax.zemytalents.ui.shared.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalentEnum
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zcdesignsystem.R as DesignSystem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZEMTBottomSheet(
    title: String,
    description: String,
    lottieUrl: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes titleIcon: Int? = null,
    enableDismissButton: Boolean = true
) {
    val lottie by rememberLottieComposition(
        LottieCompositionSpec.Url(lottieUrl)
    )

    val modalState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .wrapContentHeight()
            .statusBarsPadding(),
        sheetState = modalState,
        containerColor = colorResource(DesignSystem.color.zcds_white),
        contentColor = colorResource(DesignSystem.color.zcds_white),
        scrimColor = colorResource(DesignSystem.color.zcds_loader_translucent),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                titleIcon?.let {
                    Icon(
                        painter = painterResource(titleIcon),
                        contentDescription = null,
                        tint = colorResource(DesignSystem.color.zcds_very_dark_gray_700),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                ZEMTText(
                    text = title,
                    style = DesignSystem.style.TextAppearance_ZCDSApp_Header04,
                    modifier = Modifier.weight(1f, true)
                )

                Icon(
                    painter = painterResource(DesignSystem.drawable.zcds_ic_x_solid),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onDismissRequest),
                    tint = colorResource(DesignSystem.color.zcds_very_dark_gray_700)
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = lottie,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .heightIn(max = 200.dp),
                    contentScale = ContentScale.FillHeight
                )

                ZEMTText(
                    text = description,
                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge,
                    modifier = if (enableDismissButton) Modifier else Modifier.padding(bottom = 24.dp)
                )

                if (enableDismissButton) ZEMTButton(
                    text = stringResource(id = R.string.zemt_ok),
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth(),
                    onClick = onDismissRequest
                )
            }
        }
    }
}

@Preview
@Composable
private fun ZEMTBottomSheetPreview() {
    ZEMTBottomSheet(
        title = "Conexión rápida",
        description = "Esta conversación ayuda a conocer mejor al colaborador y saber lo que está sucediendo a corto plazo para identificar posibles obstáculos.",
        lottieUrl = "https://files.talentozeus-dev.com.mx/Utilerias/Conversaciones/lotties/Fijar+objetivos.json",
        onDismissRequest = {},
        titleIcon = getIconFromId(ZEMTTalentEnum.MOMENTO.talentId)
    )
}

@Preview
@Composable
private fun ZEMTBottomSheetPreviewWithLargeDescription() {
    ZEMTBottomSheet(
        title = "Conexión rápida",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam aliquet vulputate placerat. Quisque fermentum erat non diam luctus luctus. Vivamus lacinia diam eget aliquet elementum. Proin molestie erat quis lorem lobortis, vel maximus libero fermentum. Etiam nec interdum risus. Fusce auctor fermentum dictum. Pellentesque sapien elit, elementum a mattis eget, consequat quis leo. Quisque pretium cursus orci, eleifend gravida tortor. Nulla facilisi. Sed nibh elit, molestie et sapien et, dapibus efficitur lacus.\n" +
                "Suspendisse in nisl velit. Ut vel magna a arcu pulvinar congue a eget ex. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse potenti. Aenean ut vehicula augue. Morbi ipsum lorem, dictum sed sagittis ut, interdum vel neque. Nam a quam vestibulum, sagittis mauris nec, mollis augue. Nullam eget massa eget augue elementum porttitor. Curabitur pharetra fermentum varius. Ut placerat, ligula vitae feugiat facilisis, tortor risus porta sem, eu auctor lacus nisl et ex. Duis bibendum dignissim lectus, quis vehicula lorem cursus eget. Cras et tellus quis ex ullamcorper viverra. Ut sodales volutpat porttitor. Mauris egestas eros a condimentum volutpat.\n" +
                "Proin venenatis aliquam urna, nec sollicitudin diam porttitor at. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer sed commodo est, quis rutrum diam. Nunc aliquet dolor rutrum, fermentum tellus sit amet, bibendum nisl. Phasellus ut nunc eu lectus gravida suscipit. Vestibulum euismod enim at erat bibendum lacinia. Suspendisse dui odio, gravida nec malesuada id, venenatis at augue. Etiam eu diam est.",
        lottieUrl = "https://files.talentozeus-dev.com.mx/Utilerias/Conversaciones/lotties/Fijar+objetivos.json",
        onDismissRequest = {},
        titleIcon = getIconFromId(ZEMTTalentEnum.MOMENTO.talentId)
    )
}