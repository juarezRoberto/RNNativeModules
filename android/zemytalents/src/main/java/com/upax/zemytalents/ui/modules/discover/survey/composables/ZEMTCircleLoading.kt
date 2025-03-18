package com.upax.zemytalents.ui.modules.discover.survey.composables

import android.animation.ValueAnimator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.upax.zemytalents.R
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTCircleLoading(progress: Float, modifier: Modifier = Modifier) {

    var boxWidthInPixels by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(8.dp, clip = true, shape = CircleShape)
            .onSizeChanged { size -> boxWidthInPixels = size.width }
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = RDS.color.zcds_white)),
            color = colorResource(id = RDS.color.zcds_error),
            trackColor = colorResource(id = RDS.color.zcds_white)
        )
        AndroidView(
            factory = { context -> LottieAnimationView(context) },
            update = {
                it.setAnimation(R.raw.zemt_balance)
                it.playAnimation()
                it.repeatCount = ValueAnimator.INFINITE
            },
            modifier = Modifier
                .size(with(LocalDensity.current) { (boxWidthInPixels / 2).toDp() })
                .align(Alignment.Center)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFCCCCCC
)
@Composable
private fun ZEMTCircleLoadingPreview() {
    ZEMTCircleLoading(
        progress = 0.5f,
        modifier = Modifier.padding(50.dp)
    )
}