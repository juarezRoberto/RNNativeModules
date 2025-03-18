package com.upax.zemytalents.ui.shared.composables.talentsprofile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.upax.zcdesignsystem.expose.ZCDSColorUtils
import com.upax.zcdesignsystem.expose.ZCDSColorVariant
import com.upax.zcdesignsystem.widget.ZCDSPhotoView
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTProfileTalentState
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.domain.models.ZEMTTalentAnimationData
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTClickableIcon
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTTalentsProfile(
    stage: ZEMTTalentsProfileStages,
    data: ZEMTTalentsProfileUserUiModel,
    modifier: Modifier = Modifier,
    onTalentClick: (ZEMTTalent?) -> Unit,
    onTalentFocusListener: (ZEMTProfileTalentState) -> Unit
) {
    val fiveTalents = 5
    val context = LocalContext.current
    val style = getCircularProfileStyle(context, stage)
    val innerCircleDiameter = style.innerCircle.radius * 2
    val outerCircleDiameter = style.outerCircle.radius * 2

    val initialTalents: List<ZEMTTalent> = when (stage) {
        is ZEMTTalentsProfileStages.Discover -> {
            (0..fiveTalents).map {
                ZEMTTalent(
                    id = NO_TALENT,
                    name = "",
                    description = "",
                    isTempTalent = true,
                )
            }
        }

        is ZEMTTalentsProfileStages.Confirm, is ZEMTTalentsProfileStages.ConfirmNoAnimation -> {
            (0..fiveTalents).map {
                ZEMTTalent(
                    id = NO_TALENT,
                    name = "",
                    description = "",
                    isTempTalent = true,
                )
            }
        }

        is ZEMTTalentsProfileStages.Apply -> {
            stage.talents.take(fiveTalents).map { it.copy(isTempTalent = true) }
        }

        is ZEMTTalentsProfileStages.Complete -> {
            stage.talents.take(fiveTalents).map { it.copy(isTempTalent = false) }
        }
    }
    val talents: List<ZEMTTalent> = when (stage) {
        is ZEMTTalentsProfileStages.Discover -> {
            initialTalents
        }

        is ZEMTTalentsProfileStages.Confirm -> {
            stage.temporalTalents.take(fiveTalents).map { it.copy(isTempTalent = true) }
        }

        is ZEMTTalentsProfileStages.ConfirmNoAnimation -> {
            stage.temporalTalents.take(fiveTalents).map { it.copy(isTempTalent = true) }
        }


        is ZEMTTalentsProfileStages.Apply -> {
            stage.talents.take(fiveTalents).map { it.copy(isTempTalent = false) }
        }

        is ZEMTTalentsProfileStages.Complete -> {
            stage.talents.take(fiveTalents)
        }
    }

    val talentList = getModulesList(talents)
    val talentDelay = 1250L

    var talentAnimationList: List<ZEMTTalentAnimationData> by remember {
        mutableStateOf(
            getModulesList(initialTalents)
        )
    }
    if (stage is ZEMTTalentsProfileStages.Confirm || stage is ZEMTTalentsProfileStages.Apply) {

        // se usa para confirma y cuando actualiza la lista
//        if (isConfirmAnimation || !isApplyCompletedAnimation)
        LaunchedEffect(key1 = stage) {
            delay(500)
            talents.forEachIndexed { index, _ ->
                delay(talentDelay)
                talentAnimationList = when (stage) {
                    is ZEMTTalentsProfileStages.Confirm -> talentAnimationList.toMutableList()
                        .apply {
                            val newIndex = when (index) {
                                0 -> 4
                                1 -> 2
                                2 -> 0
                                3 -> 1
                                else -> 3
                            }
                            this[newIndex] = talentList[newIndex]
                        }

                    is ZEMTTalentsProfileStages.Apply -> talentAnimationList.toMutableList().apply {
                        val newIndex = when (index) {
                            0 -> 4
                            1 -> 3
                            2 -> 2
                            3 -> 1
                            else -> 0
                        }
                        this[newIndex] = talentList[newIndex]
                    }

                    else -> talentAnimationList.toMutableList().apply {
                        this[index] = talentList[index]

                    }
                }
                onTalentFocusListener(
                    ZEMTProfileTalentState.Label(
                        talent = talents[index].name,
                         isTheLastTalent = index == talents.size - 1)
                )
            }
            delay(talentDelay)
            onTalentFocusListener(ZEMTProfileTalentState.Done)
        }
    }

    // Misma logica que Confirm, solo que no hace delay para mostrar los talentos con el icono de lock
    if (stage is ZEMTTalentsProfileStages.ConfirmNoAnimation) {
        LaunchedEffect(key1 = stage) {
            talents.forEachIndexed { index, _ ->
                talentAnimationList = talentAnimationList.toMutableList().apply {
                    this[index] = talentList[index]
                }
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Outer Circle
        Box(
            modifier = Modifier
                .height(outerCircleDiameter)
                .width(outerCircleDiameter)
                .then(
                    if (style.outerCircle.borderWidth > 0.dp) Modifier.border(
                        style.outerCircle.borderWidth,
                        style.outerCircle.borderColor,
                        CircleShape
                    ) else Modifier
                )
                .shadow(style.shadowElevation, clip = true, shape = CircleShape)
                .background(color = style.outerCircle.color)
        )
        // Inner Circle
        Box(
            modifier = Modifier
                .height(innerCircleDiameter)
                .width(innerCircleDiameter)
                .then(
                    if (style.innerCircle.borderWidth > 0.dp) Modifier.border(
                        style.innerCircle.borderWidth,
                        style.innerCircle.borderColor,
                        CircleShape
                    ) else Modifier
                )
                .shadow(style.shadowElevation, clip = true, shape = CircleShape)
                .background(color = style.innerCircle.color)
        )
        AndroidView(
            modifier = Modifier
                .size(120.dp)
                .shadow(style.shadowElevation, clip = true, shape = CircleShape),
            factory = {
                ZCDSPhotoView(it)
            }, update = {
                it.apply {
                    setInfo(data.userProfileUrl, data.userName)
                    enableBackgroundThemeColor(false)
                    setTextSize(RDS.dimen.zcds_txt_xxxxlarge)
                    setInfoBackgroundColorRes(RDS.color.zcds_white)
                    setBackgroundStrokeWidth(style.profilePictureStrokeWidth.value)
                    setInfoBackgroundStrokeColor(style.profilePictureStrokeColor.toArgb())
                    enableTextThemeColor(style.profileLettersColor == null)
                    style.profileLettersColor?.let(::setTextColorRes)
                }
            }
        )

        Layout(
            content = {
                talentAnimationList.forEachIndexed { index, talent ->
                    val iconSize = getSize(index)
                    val miniIconSize = getMiniIconSize(index)
                    ZEMTClickableIcon(
                        talentAnimationData = talent,
                        modifier = Modifier
                            .graphicsLayer(alpha = 1f),
                        onClick = { talentId ->
                            if (talentId == NO_TALENT) return@ZEMTClickableIcon
                            onTalentClick(talents.firstOrNull { talent -> talent.id == talentId })
                        },
                        iconSizeDp = iconSize,
                        miniIconSize = miniIconSize,
                        enableClick = stage is ZEMTTalentsProfileStages.Complete,
                    )
                }
            },
            modifier = Modifier
                .height(innerCircleDiameter)
                .width(innerCircleDiameter),
            measurePolicy = { measurables, constraints ->
                // Medimos los elementos hijos
                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints)
                }

                // Calculamos el radio del arco
                val radius = constraints.maxWidth / 2

                // Calculamos el ángulo de inicio y fin del arco (en radianes)
                val startAngle = Math.PI
                val endAngle = Math.PI * 2

                // Calculamos el ángulo de separación entre los elementos
                val angleStep = (startAngle - endAngle) / 4

                // Calculamos el centro de la pantalla
                val centerX = constraints.maxWidth / 2f
                val centerY = constraints.maxHeight / 2f

                // Calculamos la posición inicial (primer elemento de iconList)
                val initialAngle = (3 * Math.PI) / 2
                // Calculamos la posición de cada elemento en el arco
                val finalAngles = mutableListOf<Double>()
                val initialAngleList =
                    mutableListOf(initialAngle, initialAngle, initialAngle)
                var currentAngle = initialAngle
                placeables.forEachIndexed { index, _ ->
                    finalAngles.add(currentAngle)

                    val multiplier = index + 1
                    currentAngle += (if (index % 2 == 0) -angleStep else +angleStep) * multiplier
                }

                val offset = 12
                val negativeOffset = -angleStep / offset
                val positiveOffset = angleStep / offset
                initialAngleList.add(initialAngle - angleStep + negativeOffset)
                initialAngleList.add(initialAngle + angleStep + positiveOffset)
                // Devolvemos las medidas de los hijos con sus posiciones animadas
                layout(
                    width = constraints.maxWidth,
                    height = constraints.maxHeight,
                    alignmentLines = emptyMap(),
                ) {

//                    val angleAnimatables = List(talentAnimationList.size) { remember { Animatable(0f) } }

                    placeables.forEachIndexed { index, placeable ->
                        val tempAngle = finalAngles[index]
                        val finalAngle =
                            if (index == 1) tempAngle + negativeOffset else if (index == 2) tempAngle + positiveOffset else tempAngle
                        val x =
                            centerX + radius * cos(finalAngle).toFloat() - placeable.width / 2f
                        val y =
                            centerY - radius * sin(finalAngle).toFloat() - placeable.height / 2f
                        placeable.placeRelative(
                            x = x.toInt(), y = y.toInt(),
                            zIndex = when (index) {
                                3, 4 -> 0f
                                2, 1 -> 1f
                                else -> 20f
                            }
                        )
                    }
                }
            }
        )
    }
}

private fun getSize(index: Int) = when (index) {
    3, 4 -> 40.dp
    1, 2 -> 50.dp
    else -> 60.dp
}

private fun getMiniIconSize(index: Int) = when (index) {
    3, 4 -> 18.dp
    1, 2 -> 22.5.dp
    else -> 27.dp
}

private fun getModulesList(talents: List<ZEMTTalent>): List<ZEMTTalentAnimationData> {
    val maxTalents = 5
    val talentData = mutableListOf<ZEMTTalentAnimationData>()
    for (i in 0 until maxTalents) {
        val talent = talents.getOrNull(i)
        val icon = talent?.getIconFromId() ?: R.drawable.zemt_ic_talent_none
        talentData.add(
            ZEMTTalentAnimationData(
                talentId = talent?.id ?: NO_TALENT,
                isTemp = talent?.isTempTalent ?: false,
                order = i + 1,
                icon = icon,
                talentName = talent?.name.orEmpty()
            )
        )
    }

    return talentData
}

private fun getCircularProfileStyle(
    context: Context,
    state: ZEMTTalentsProfileStages
): ZEMTTalentsProfileStyleUiModel {

    val profileStrokeColor = when (state) {
        is ZEMTTalentsProfileStages.ConfirmNoAnimation,
        is ZEMTTalentsProfileStages.Confirm,
        is ZEMTTalentsProfileStages.Discover,
        is ZEMTTalentsProfileStages.Apply -> {
            context.getColor(RDS.color.zcds_mid_gray_500)
        }

        else -> ZCDSColorUtils.getPrimaryColor()
    }

    val innerCircleBorderColor = when (state) {
        is ZEMTTalentsProfileStages.ConfirmNoAnimation,
        is ZEMTTalentsProfileStages.Confirm,
        is ZEMTTalentsProfileStages.Discover,
        is ZEMTTalentsProfileStages.Apply -> {
            context.getColor(RDS.color.zcds_mid_gray_500)
        }

        else -> ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.VeryLight)
    }

    val innerCircleColor = when (state) {
        is ZEMTTalentsProfileStages.ConfirmNoAnimation,
        is ZEMTTalentsProfileStages.Confirm,
        is ZEMTTalentsProfileStages.Discover,
        is ZEMTTalentsProfileStages.Apply -> {
            context.getColor(RDS.color.zcds_very_light_gray_200)
        }

        else -> ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.ExtraLight)
    }

    val outerCircleBorderColor = when (state) {
        is ZEMTTalentsProfileStages.ConfirmNoAnimation,
        is ZEMTTalentsProfileStages.Confirm,
        is ZEMTTalentsProfileStages.Discover,
        is ZEMTTalentsProfileStages.Apply -> {
            context.getColor(RDS.color.zcds_white)
        }

        else -> ZCDSColorUtils.getPrimaryColor(ZCDSColorVariant.ExtraLight)
    }

    val profileLettersColor = when (state) {
        is ZEMTTalentsProfileStages.ConfirmNoAnimation,
        is ZEMTTalentsProfileStages.Confirm,
        is ZEMTTalentsProfileStages.Discover,
        is ZEMTTalentsProfileStages.Apply -> {
            RDS.color.zcds_dark_gray_600
        }

        else -> null
    }

    return ZEMTTalentsProfileStyleUiModel(
        profilePictureStrokeColor = Color(profileStrokeColor),
        profileLettersColor = profileLettersColor,
        innerCircle = ZEMTTalentsProfileCircleUiModel(
            color = Color(innerCircleColor),
            radius = 95.dp,
            borderWidth = 3.dp,
            borderColor = Color(innerCircleBorderColor)
        ),
        outerCircle = ZEMTTalentsProfileCircleUiModel(
            color = Color(context.getColor(RDS.color.zcds_white)),
            radius = 133.5.dp,
            borderWidth = 3.dp,
            borderColor = Color(outerCircleBorderColor)
        ),
    )
}

@Preview(showBackground = true, name = "Discover")
@Composable
private fun ZEMTTalentsProfileDiscoverPreview() {
    ZEMTTalentsProfile(
        stage = ZEMTTalentsProfileStages.Discover,
        modifier = Modifier
            .fillMaxWidth()
            .height(267.dp),
        data = ZEMTTalentsProfileUserUiModel(
            userName = "Robert Juarez",
            userProfileUrl = "",
        ),
        onTalentClick = {},
        onTalentFocusListener = {}
    )
}

@Preview(showBackground = true, name = "Confirm")
@Composable
private fun ZEMTTalentsProfileConfirmPreview() {
    ZEMTTalentsProfile(
        stage = ZEMTTalentsProfileStages.Confirm(ZEMTMockModulesData.getTalents()),
        modifier = Modifier
            .fillMaxWidth()
            .height(267.dp),
        data = ZEMTTalentsProfileUserUiModel(
            userName = "Robert Juarez",
            userProfileUrl = "",
        ),
        onTalentClick = {},
        onTalentFocusListener = {}
    )
}

@Preview(showBackground = true, name = "Apply")
@Composable
private fun ZEMTTalentsProfileApplyPreview() {
    ZEMTTalentsProfile(
        stage = ZEMTTalentsProfileStages.Apply(ZEMTMockModulesData.getTalents()),
        modifier = Modifier
            .fillMaxWidth()
            .height(267.dp),
        data = ZEMTTalentsProfileUserUiModel(
            userName = "Robert Juarez",
            userProfileUrl = "",
        ),
        onTalentClick = {},
        onTalentFocusListener = {}
    )
}

@Preview(showBackground = true, name = "Complete")
@Composable
private fun ZEMTTalentsProfileCompletePreview() {
    ZEMTTalentsProfile(
        stage = ZEMTTalentsProfileStages.Complete(ZEMTMockModulesData.getTalents()),
        modifier = Modifier,
        data = ZEMTTalentsProfileUserUiModel(
            userName = "Robert Juarez",
            userProfileUrl = "",
        ),
        onTalentClick = {},
        onTalentFocusListener = {}
    )
}

const val NO_TALENT = -1