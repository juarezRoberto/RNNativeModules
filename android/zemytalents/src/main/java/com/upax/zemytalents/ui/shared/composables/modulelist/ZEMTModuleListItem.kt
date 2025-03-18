package com.upax.zemytalents.ui.shared.composables.modulelist

import android.animation.ValueAnimator
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as ZCDSR


/**
 * Module info with basic talent info
 *
 * @property progress a valuew between 0 and 1 that represents the progress of the module
 * */
@Composable
fun ZEMTModuleListItem(
    title: String,
    description: String,
    progress: Float,
    @RawRes iconLottie: Int,
    isCurrent: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .alpha(if (isCurrent) 1f else 0.5f)
            .padding(4.dp)
            .width(250.dp),
        shadowElevation = 4.dp,
        color = Color(LocalContext.current.getColor(ZCDSR.color.zcds_white)),
        shape = RoundedCornerShape(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_small))
    ) {
        Column(modifier = Modifier
            .padding(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_small))) {
            ZEMTText(
                text = title,
                style = ZCDSR.style.TextAppearance_ZCDSApp_Header06,
            )
            Spacer(modifier = Modifier
                .height(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_large)))
            Row(verticalAlignment = Alignment.CenterVertically) {
                AndroidView(
                    factory = { context -> LottieAnimationView(context) },
                    update = {
                        it.setAnimation(iconLottie)
                        it.repeatCount = ValueAnimator.INFINITE
                        it.playAnimation()
                    },
                    modifier = Modifier
                        .height(41.dp)
                        .widthIn(max = 36.dp)
                )
                ZEMTText(
                    text = description,
                    style = ZCDSR.style.TextAppearance_ZCDSApp_BodySmall
                )
            }
            Spacer(modifier = Modifier
                .height(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_large)))
            val context = LocalContext.current
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_small))
                    .clip(RoundedCornerShape(dimensionResource(ZCDSR.dimen.zcds_margin_padding_size_small))),
                progress = { progress },
                color = colorResource(id = ZCDSR.color.zcds_success),
                trackColor = Color(context.getColor(ZCDSR.color.zcds_extra_light_gray_100)),
                strokeCap = StrokeCap.Round,
                gapSize = (-15).dp,
                drawStopIndicator = { },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ZMTModuleListItemPreview() {
    Column {
        ZEMTModuleListItem(
            title = "Descubre tus talentos",
            description = "Descubre tus fortalezas y lleva al máximo tu potencial",
            progress = 0.25f,
            iconLottie = R.raw.zemt_telescope,
            modifier = Modifier,
            isCurrent = false
        )
        ZEMTModuleListItem(
            title = "Confirma tus talentos",
            description = "Descubre tus fortalezas y lleva al máximo tu potencial",
            progress = 0.01f,
            iconLottie = R.raw.zemt_rocket,
            modifier = Modifier,
            isCurrent = true,
        )
        ZEMTModuleListItem(
            title = "Aplica tus talentos",
            description = "Descubre tus fortalezas y lleva al máximo tu potencial",
            progress = 0.01f,
            iconLottie = R.raw.zemt_degree,
            modifier = Modifier,
            isCurrent = false,
        )
    }
}