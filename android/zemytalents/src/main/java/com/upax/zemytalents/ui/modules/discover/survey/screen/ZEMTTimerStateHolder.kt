package com.upax.zemytalents.ui.modules.discover.survey.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ZEMTTimerStateHolder(
    private val coroutineScope: CoroutineScope,
    private val durationMillis: Int = 5000,
    private val onTimerFinished: () -> Unit,
    private val targetValue: Float = 1f
) {
    val timer = Animatable(0f)
    private var job: Job? = null
    private var remainingDurationMillis = durationMillis

    fun startTimer() {
        job?.cancel()
        job = coroutineScope.launch {
            timer.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = remainingDurationMillis,
                    easing = LinearEasing
                )
            )
            onTimerFinished()
        }
    }

    fun pauseTimer() {
        job?.cancel()
        remainingDurationMillis = ((1 - timer.value / targetValue) * durationMillis).toInt()
    }

    fun cancelTimer() {
        job?.cancel()
        coroutineScope.launch {
            timer.snapTo(0f)
            remainingDurationMillis = durationMillis
        }
    }

    fun resumeTimer() {
        if (job?.isActive == false) {
            startTimer()
        }
    }

    fun restartTimer() {
        job?.cancel()
        coroutineScope.launch {
            timer.snapTo(0f)
            remainingDurationMillis = durationMillis
            startTimer()
        }
    }
}

@Composable
internal fun rememberTimerStateHolder(
    durationMillis: Int,
    targetValue: Float = 1f,
    onTimerFinished: () -> Unit
): ZEMTTimerStateHolder {
    val scope = rememberCoroutineScope()
    return remember { ZEMTTimerStateHolder(scope, durationMillis, onTimerFinished, targetValue) }
}
