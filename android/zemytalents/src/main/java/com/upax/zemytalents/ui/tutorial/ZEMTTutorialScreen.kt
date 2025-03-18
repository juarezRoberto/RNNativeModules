package com.upax.zemytalents.ui.tutorial

import android.animation.Animator
import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.shared.composables.ZEMTButton

@Composable
internal fun ZEMTTutorialScreen(
    onTutorialFinished: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ZEMTTutorialAnimation(onTutorialFinished)
        ZEMTButton(stringResource(R.string.zemt_skip), onClick = onTutorialFinished)
    }
}

@Composable
private fun ZEMTTutorialAnimation(
    onTutorialFinished: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LottieAnimationView(context).apply {
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        onTutorialFinished.invoke()
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}

                })
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { lottie ->
            lottie.setAnimation(R.raw.zemt_home_tutorial)
            lottie.playAnimation()
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTTutorialScreenPreview() {
    ZEMTTutorialScreen(
        onTutorialFinished = {}
    )
}