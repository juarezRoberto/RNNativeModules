package com.upax.zemytalents.ui.conversations.qrscanner

import android.graphics.Typeface
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.budiyev.android.codescanner.ButtonPosition
import com.budiyev.android.codescanner.CodeScannerView
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTQrScannerFragment(
    navigateBack: () -> Unit,
    bindScanner: (CodeScannerView) -> Unit,
    modifier: Modifier = Modifier,
    showNotValidQr: Boolean = false
) {
    val localDensity = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val qrFrameSize = .75f
    val textSize = 16.sp
    val textPadding = 76.dp
    val textOffset: Dp = (screenWidth * qrFrameSize / 2) + textSize.value.dp + textPadding
    val whiteColor = colorResource(DesignSystem.color.zcds_white)
    Box(modifier = modifier) {
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .zIndex(1f)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = DesignSystem.drawable.zcds_ic_x_solid),
                contentDescription = null,
                tint = whiteColor,
                modifier = Modifier.size(32.dp)
            )
        }

        AnimatedVisibility(visible = showNotValidQr, modifier = Modifier.align(Alignment.BottomCenter)
            .zIndex(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 48.dp)
                    .background(color = whiteColor, shape = RoundedCornerShape(8.dp))
            ) {
                ZEMTText(
                    text = stringResource(id = R.string.zemt_qr_not_valid),
                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
                )
            }
        }

        ZEMTText(
            text = stringResource(id = R.string.zemt_qr_scan),
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.Center)
                .offset(y = -textOffset),
            color = whiteColor.toArgb(),
            style = DesignSystem.style.TextAppearance_ZCDSApp_BodyLarge,
            textStyle = Typeface.BOLD,
            fontSize = textSize
        )

        AndroidView(factory = { context ->
            CodeScannerView(context).apply {
                flashButtonPosition = ButtonPosition.BOTTOM_START
                isAutoFocusButtonVisible = false
                isFlashButtonVisible = false
                frameSize = qrFrameSize
                frameCornersSize = localDensity.run { 20.dp.roundToPx() }
                frameThickness = localDensity.run { 4.dp.roundToPx() }
                frameCornersRadius = localDensity.run { 12.dp.roundToPx() }
                bindScanner(this)
            }
        })
    }
}

@Preview
@Composable
private fun ZEMTQrScannerFragmentPreview() {
    ZEMTQrScannerFragment(
        modifier = Modifier.systemBarsPadding(),
        bindScanner = {},
        navigateBack = {},
        showNotValidQr = false
    )
}

@Preview
@Composable
private fun ZEMTQrScannerFragmentPreview2() {
    ZEMTQrScannerFragment(
        modifier = Modifier.systemBarsPadding(),
        bindScanner = {},
        navigateBack = {},
        showNotValidQr = true
    )
}