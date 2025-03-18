package com.upax.zemytalents.ui.modules.discover.survey.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.discover.survey.shapes.ZEMTTextGlobeShapeWithTriangleAtRight
import com.upax.zemytalents.ui.modules.discover.survey.shapes.ZEMTTextGlobeShapeWithTriangleInMiddle
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTProgressBar(
    currentProgress: Int,
    maxProgress: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

        ZEMTText(
            text = stringResource(id = R.string.zemt_progress),
            color = colorResource(id = RDS.color.zcds_dark_gray_600).toArgb(),
            modifier = Modifier
                .padding(start = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium))
        )

        ZEMTLinearProgressBarWithTextPercentage(
            currentProgress = currentProgress,
            maxProgress = maxProgress
        )
    }
}

@Composable
private fun ZEMTLinearProgressBarWithTextPercentage(currentProgress: Int, maxProgress: Int) {

    var progressBarWidth by remember { mutableIntStateOf(0) }

    var progress = currentProgress.toFloat() / maxProgress.toFloat()
    if (!progress.isFinite()) progress = 0f

    val animatedTextProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "animated text progress"
    )

    val animatedProgressIndicator by animateFloatAsState(
        targetValue = progress.coerceIn(0.01f, 1f),
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "animated progress indicator"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_mini))
            .wrapContentSize(unbounded = false)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(
                    start = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium),
                    end = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large),
                )
                .clip(RoundedCornerShape(dimensionResource(id = RDS.dimen.zcds_round_corners_small)))
                .onGloballyPositioned { progressBarWidth = it.size.width },
            progress = { animatedProgressIndicator },
            color = colorResource(id = RDS.color.zcds_success),
            trackColor = Color(LocalContext.current.getColor(RDS.color.zcds_light_gray_300)),
            strokeCap = StrokeCap.Round,
            gapSize = (-15).dp,
            drawStopIndicator = { }
        )

        ZEMTTextPercentage(animatedTextProgress, progressBarWidth)
    }
}

@Composable
private fun ZEMTTextPercentage(progress: Float, progressBarWidth: Int) {

    val textPercentageWidth = 32.dp
    val textPercentageHeight = 16.dp
    val triangleHeight = 6.dp

    val maxPaddingInPixels = progressBarWidth - with(LocalDensity.current) {
        4.dp.toPx() + (textPercentageWidth.toPx() / 2)
    }
    var endTriangleShape = false

    val paddingStartInPixels = maxPaddingInPixels.let { maxPadding ->
        val paddingToAddInPixels = progressBarWidth * progress
        when {
            maxPadding <= 0f -> 0f
            paddingToAddInPixels >= maxPadding -> {
                endTriangleShape = true
                maxPadding
            }

            else -> paddingToAddInPixels
        }
    }

    Box(
        modifier = Modifier
            .padding(start = with(LocalDensity.current) { paddingStartInPixels.toDp() })
            .shadow(
                8.dp,
                clip = true,
                shape = if (endTriangleShape) {
                    ZEMTTextGlobeShapeWithTriangleAtRight(
                        width = textPercentageWidth,
                        textContainerHeight = textPercentageHeight,
                        triangleHeight = triangleHeight
                    )
                } else {
                    ZEMTTextGlobeShapeWithTriangleInMiddle(
                        width = textPercentageWidth,
                        textContainerHeight = textPercentageHeight,
                        triangleHeight = triangleHeight
                    )
                }
            )
            .height(textPercentageHeight + triangleHeight)
            .width(textPercentageWidth)
            .background(colorResource(id = RDS.color.zcds_white))
    ) {
        val progressPercentage = if (progress == 0f) {
            0f
        } else {
            (progress * 100).coerceAtLeast(1f)
        }
        val roundedPercentage = progressPercentage.toInt()
        ZEMTText(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = triangleHeight),
            text = "$roundedPercentage%",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = RDS.color.zcds_dark_gray_600).toArgb()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview0Answers() {
    ZEMTProgressBar(
        currentProgress = 50,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview1Answer() {
    ZEMTProgressBar(
        currentProgress = 50,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview2Answers() {
    ZEMTProgressBar(
        currentProgress = 2,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview3Answers() {
    ZEMTProgressBar(
        currentProgress = 3,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview4Answers() {
    ZEMTProgressBar(
        currentProgress = 4,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview98Percent() {
    ZEMTProgressBar(
        currentProgress = 185,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview99Percent() {
    ZEMTProgressBar(
        currentProgress = 189,
        maxProgress = 190
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTProgressBarPreview100Percent() {
    ZEMTProgressBar(
        currentProgress = 190,
        maxProgress = 190
    )
}