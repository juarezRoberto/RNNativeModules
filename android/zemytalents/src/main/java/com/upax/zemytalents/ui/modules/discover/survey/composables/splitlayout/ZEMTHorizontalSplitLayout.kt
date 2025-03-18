package com.upax.zemytalents.ui.modules.discover.survey.composables.splitlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.upax.zcdesignsystem.R
import com.upax.zemytalents.ui.shared.animatePlacement

@Composable
internal fun ZEMTHorizontalSplitLayout(
    modifier: Modifier = Modifier,
    leftColor: Color = colorResource(R.color.zcds_extra_light_gray_100),
    rightColor: Color = colorResource(R.color.zcds_white),
    backgroundContent: ZEMTSplitLayoutBackgroundContent? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Box(modifier = modifier) {

        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f, true)
                    .fillMaxHeight()
                    .background(leftColor)
            )
            Box(
                modifier = Modifier
                    .weight(1f, true)
                    .fillMaxHeight()
                    .background(rightColor)
            )
        }


        when (backgroundContent?.alignment) {
            ZEMTSplitLayoutBackgroundContent.Alignment.LEFT,
            ZEMTSplitLayoutBackgroundContent.Alignment.RIGHT -> {
                Box(
                    modifier = Modifier
                        .animatePlacement()
                        .fillMaxHeight()
                        .align(backgroundContent.composeAlignment)
                        .fillMaxWidth(0.5f)
                        .background(backgroundContent.color)
                )
            }

            ZEMTSplitLayoutBackgroundContent.Alignment.FULL -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundContent.color))
            }

            null -> Unit
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            content = content,
            verticalArrangement = Arrangement.Center
        )
    }
}

@Composable
@Preview
fun ZEMTHorizontalSplitLayoutPreview() {
    ZEMTHorizontalSplitLayout {
        Text(
            text = "Hello world",
            modifier = Modifier.wrapContentSize(),
            fontSize = 24.sp
        )
    }
}

@Composable
@Preview
fun ZEMTHorizontalSplitLayoutPreviewWithBackground() {
    ZEMTHorizontalSplitLayout(
        backgroundContent = ZEMTSplitLayoutBackgroundContent(
            color = Color.Red,
            alignment = ZEMTSplitLayoutBackgroundContent.Alignment.RIGHT
        )
    ) {
        Text(
            text = "Hello world",
            modifier = Modifier.wrapContentSize(),
            fontSize = 24.sp
        )
    }
}