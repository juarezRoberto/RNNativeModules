package com.upax.zemytalents.ui.conversations.qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.upax.zccommon.extensions.SPACE
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant
import com.upax.zcdesignsystem.widget.ZCDSPhotoView
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.common.ZEMTQrFunctions.generateQrData
import com.upax.zemytalents.common.ZEMTQrFunctions.parse
import com.upax.zemytalents.common.ZEMTQrFunctions.toLocalDateTime
import com.upax.zemytalents.common.ZEMTStringExtensions.capitalizeText
import com.upax.zemytalents.ui.conversations.qr.composables.ZEMTCustomQr
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import kotlinx.coroutines.delay
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTQrProfileInfo(
    userData: ZCSIUser,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onShareAction: () -> Unit = {},
) {
    val imageSize = 100.dp
    val imageOffset = imageSize / 3f
    val backgroundColor = colorResource(DesignSystem.color.zcds_white)
    var shareAction by remember { mutableStateOf(false) }

    LaunchedEffect(shareAction) {
        if (shareAction) {
            delay(2_000)
            shareAction = false
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .offset(y = -imageOffset)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        onDismissRequest()
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ZEMTQrCard(
                userData = userData,
                imageOffset = imageOffset,
                imageSize = imageSize,
                backgroundColor = backgroundColor,
                modifier = Modifier
            )


            Image(
                painter = painterResource(DesignSystem.drawable.zcds_ic_share_outlined),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    )
                    .padding(12.dp)
                    .pointerInput(Unit) {
                        onShareAction()
                    }
            )
        }
    }
}

@Composable
fun ZEMTQrCard(
    userData: ZCSIUser,
    imageOffset: Dp,
    imageSize: Dp,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val qrData = userData.generateQrData()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                ZCDSPhotoView(context).apply {
                    setInfo(
                        name = userData.name,
                        url = userData.photo
                    )
                }
            },
            modifier = Modifier
                .offset(y = imageOffset)
                .zIndex(1f)
                .size(imageSize)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    shape = CircleShape,
                    color = backgroundColor
                )
                .pointerInput(Unit) { detectTapGestures { } }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 40.dp)
                .pointerInput(Unit) { detectTapGestures { } }
        ) {
            ZEMTText(
                text = userData.name.capitalizeText(),
                style = DesignSystem.style.TextAppearance_ZCDSApp_Header01,
                modifier = Modifier
                    .padding(top = 68.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            ZEMTText(
                text = (userData.lastName + String.SPACE + userData.secondLastName).capitalizeText(),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge
            )

            ZEMTCustomQr(
                qrData = qrData.getDataToGenerateQr(),
                mainColor = colorResource(DesignSystem.color.zcds_very_dark_gray_700),
                mainLightVariantColor = Color(
                    ZCDSColorUtils.getColorVariant(
                        LocalContext.current,
                        DesignSystem.color.zcds_very_dark_gray_700,
                        ZCDSColorVariant.ExtraLight
                    )
                ),
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .size(200.dp)
            )
            ZEMTText(
                text = stringResource(R.string.zemt_qr_title),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                color = colorResource(DesignSystem.color.zcds_dark_gray_600).toArgb(),
                textAlign = TextAlign.Center
            )

            ZEMTText(
                text = stringResource(R.string.zemt_qr_date, qrData.createdAt.toLocalDateTime().parse()),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                color = colorResource(DesignSystem.color.zcds_very_dark_gray_700).toArgb(),
                modifier = Modifier.padding(top = 24.dp, bottom = 40.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ZEMTShareableQr(userData: ZCSIUser, modifier: Modifier = Modifier) {
    val imageSize = 100.dp
    val imageOffset = imageSize / 3f
    val backgroundColor = colorResource(DesignSystem.color.zcds_white)
    Box(modifier = modifier) {
        ZEMTQrCard(
            userData = userData,
            imageOffset = imageOffset,
            imageSize = imageSize,
            backgroundColor = backgroundColor,
            modifier = Modifier
                .background(colorResource(DesignSystem.color.zcds_extra_light_gray_100))
                .padding(horizontal = 32.dp)
                .padding(top = 16.dp)
                .padding(bottom = 32.dp)
        )
    }
}

@Preview
@Composable
private fun ZEMTQrProfileInfoPreview() {
    val userData = ZCSIUser()
    ZEMTQrProfileInfo(userData = userData)
}