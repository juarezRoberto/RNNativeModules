package com.upax.zemytalents.ui.conversations.qr.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.R
import com.upax.zemytalents.ui.conversations.qr.data.ZEMTBrushColor
import com.upax.zemytalents.ui.conversations.qr.data.ZEMTColorPainter
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogoPadding
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import com.upax.zcdesignsystem.R as RDS

@Composable
fun ZEMTCustomQr(
    modifier: Modifier = Modifier,
    qrData: String = String.EMPTY,
    mainColor: Color,
    mainLightVariantColor: Color
) {
    val logoPainter = ZEMTColorPainter(
        painter = painterResource(R.drawable.zcds_logo_icon_black),
        color = mainColor
    )
    Image(
        painter = rememberQrCodePainter(
            data = qrData
        ) {
            logo {
                painter = logoPainter
                size = 0.4f
                padding = QrLogoPadding.Accurate(.0f)
            }

            colors {
                dark = ZEMTBrushColor {
                    Brush.radialGradient(
                        colorStops = arrayOf(
                            0f to mainColor,
                            .5f to mainColor,
                            .5f to mainLightVariantColor,
                            1f to mainLightVariantColor,
                        ),
                        radius = it / 3f,
                        center = Offset(it / 2, it / 2)
                    )
                }
                light = QrBrush.solid(Color.Transparent)
                frame = QrBrush.solid(mainColor)
                ball = QrBrush.solid(mainColor)
            }

            shapes {
                // corner big shapes
                frame = QrFrameShape.roundCorners(corner = .25f)
                // shape inside the frame
                ball = QrBallShape.roundCorners(radius = .25f)
                darkPixel = QrPixelShape.circle()
            }
        },
        contentDescription = null,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ZEMTCustomQrPreview() {
    ZEMTCustomQr(
        modifier = Modifier.size(200.dp),
        mainColor = colorResource(RDS.color.zcds_very_dark_gray_700),
        mainLightVariantColor = colorResource(RDS.color.zcds_very_dark_gray_700)
    )
}