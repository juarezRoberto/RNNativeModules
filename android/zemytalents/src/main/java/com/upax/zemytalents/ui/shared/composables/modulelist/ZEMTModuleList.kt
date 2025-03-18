package com.upax.zemytalents.ui.shared.composables.modulelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import com.upax.zemytalents.ui.modules.discover.survey.screen.rememberTimerStateHolder
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.models.ZEMTModuleUiModel
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTModuleList(
    moduleList: List<ZEMTModuleUiModel>,
    discoverModuleProgress: Float,
    navigateToModule: (moduleId: ZEMTModuleStage) -> Unit,
    scrollTarget: ZEMTModuleStage,
    modifier: Modifier = Modifier,
    scrollPrevious: ZEMTModuleStage? = null,
) {
    val scrollState = rememberScrollState()

    var currentItemPosition = 0f
    var prevItemPosition = 0f

    var enableScroll by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .width(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large)),
            thickness = 0.dp,
            color = Color.Transparent
        )
        moduleList
            .forEach { module ->
                val isDiscoverModule =
                    module.stage == ZEMTModuleStage.DISCOVER && !module.isComplete
                val moduleProgress =
                    if (isDiscoverModule) discoverModuleProgress else if (module.isComplete) 1f else module.progress

                val isTargetModule = scrollTarget == module.stage
                val isPrevTargetModule = scrollPrevious == module.stage
                val isPrevScrollSet = scrollPrevious != null
                var isCurrent by remember { mutableStateOf(scrollPrevious == module.stage || isTargetModule && isPrevScrollSet.not()) }

                val timerState = rememberTimerStateHolder(
                    durationMillis = 400,
                    targetValue = moduleProgress,
                    onTimerFinished = {
                        if (isPrevScrollSet) {
                            isCurrent = isTargetModule
                            enableScroll = true
                        }
                    }
                )

                LaunchedEffect(Unit) {
                    timerState.startTimer()
                }


                ZEMTModuleListItem(
                    title = module.name,
                    description = module.moduleDesc,
                    progress = timerState.timer.value,
                    isCurrent = isCurrent,
                    iconLottie = module.getIcon(),
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            val position =
                                calculateItemPosition(module.stage, coords.positionInRoot().x)

                            if (isTargetModule) {
                                currentItemPosition = position
                            }

                            if (isPrevTargetModule) {
                                prevItemPosition = position
                            }

                            if ((isTargetModule || isPrevTargetModule) && !isPrevScrollSet) {
                                enableScroll = true
                            }
                        }
                        .clickable(enabled = isCurrent) { navigateToModule(module.stage) }
                )
            }
        HorizontalDivider(
            modifier = Modifier
                .width(dimensionResource(id = RDS.dimen.zcds_margin_padding_size_large)),
            thickness = 0.dp,
            color = Color.Transparent
        )
    }

    LaunchedEffect(key1 = enableScroll) {

        //scroll when previousTarget is selected
        if (scrollPrevious != null) {
            scrollState.animateScrollBy(prevItemPosition)
        }

        //scroll when previous scroll end its animation
        if (enableScroll) scrollState.animateScrollBy(currentItemPosition)
    }
}

private fun calculateItemPosition(moduleId: ZEMTModuleStage, xPosition: Float): Float {
    return when (moduleId) {
        ZEMTModuleStage.DISCOVER -> 0f
        ZEMTModuleStage.APPLY -> xPosition
        else -> xPosition - xPosition / 4 // Item centered in screen
    }
}

@Preview(showBackground = true)
@Composable
private fun ZEMTModuleListDiscoverPreview() {
    ZEMTModuleList(
        moduleList = ZEMTMockModulesData.getModules(),
        discoverModuleProgress = 0.7f,
        navigateToModule = {},
        scrollTarget = ZEMTModuleStage.DISCOVER,
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTModuleListConfirmPreview() {
    ZEMTModuleList(
        moduleList = ZEMTMockModulesData.getModules(),
        discoverModuleProgress = 0.7f,
        navigateToModule = {},
        scrollTarget = ZEMTModuleStage.CONFIRM,
    )
}

@Preview(showBackground = true)
@Composable
private fun ZEMTModuleListApplyPreview() {
    ZEMTModuleList(
        moduleList = ZEMTMockModulesData.getModules(),
        discoverModuleProgress = 0.7f,
        navigateToModule = {},
        scrollTarget = ZEMTModuleStage.APPLY,
    )
}