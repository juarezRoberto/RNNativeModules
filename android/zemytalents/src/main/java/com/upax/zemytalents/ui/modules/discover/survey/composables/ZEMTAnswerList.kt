package com.upax.zemytalents.ui.modules.discover.survey.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.discover.survey.mock.ZEMTDiscoverMockData
import com.upax.zcdesignsystem.R as RDS
import com.upax.zemytalents.ui.modules.discover.survey.model.ZEMTDiscoverAnswerUiModel
import com.upax.zemytalents.ui.modules.discover.survey.shapes.ZEMTArcShape
import com.upax.zemytalents.ui.shared.composables.ZEMTButton
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import kotlin.math.cos
import kotlin.math.sin

private typealias AnswerIndex = Int

@Composable
internal fun ZEMTAnswerList(
    answers: List<ZEMTDiscoverAnswerUiModel>,
    onAnswerSelected: (answerSelected: ZEMTDiscoverAnswerUiModel) -> Unit,
    onAnswerPreselected: (answerPreselected: ZEMTDiscoverAnswerUiModel) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedAnswerIndex by rememberSaveable { mutableStateOf<AnswerIndex?>(null) }

    val answerSelected = selectedAnswerIndex?.let { index ->
        answers.getOrNull(index)
    }

    LaunchedEffect(selectedAnswerIndex) {
        answers.getOrNull(selectedAnswerIndex ?: -1)?.let(onAnswerPreselected)
    }

    ZEMTSelectorAnswerBackground(modifier = modifier) {

        ZEMTAnswerIcons(
            answers = answers,
            selectedAnswerIndex = selectedAnswerIndex,
            onAnswerSelected = { selectedAnswerIndex = it }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZEMTText(
                text = answerSelected?.text.orEmpty(),
                fontSize = 16.sp,
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium)
                ),
                textAlign = TextAlign.Center,
            )

            ZEMTButton(
                text = stringResource(id = R.string.zemt_continue),
                enabled = selectedAnswerIndex != null,
                modifier = Modifier.padding(
                    top = dimensionResource(id = RDS.dimen.zcds_margin_padding_size_medium)
                ).testTag("confirm answer"),
                onClick = {
                    onAnswerSelected(answerSelected!!)
                    selectedAnswerIndex = null
                }
            )
        }

    }
}

@Composable
private fun ZEMTSelectorAnswerBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .shadow(elevation = 12.dp, clip = true, shape = ZEMTArcShape())
                .background(color = colorResource(id = RDS.color.zcds_white))
                .fillMaxSize()
        )
        content()
    }
}

@Composable
private fun ZEMTAnswerIcons(
    answers: List<ZEMTDiscoverAnswerUiModel>,
    selectedAnswerIndex: AnswerIndex?,
    onAnswerSelected: (AnswerIndex) -> Unit
) {
    Layout(
        content = {
            answers.forEachIndexed { index, answer ->
                ZEMTAnswerIcon(
                    unselectedIcon = answer.iconUnselected,
                    selectedIcon = answer.iconSelected,
                    isEnabled = answer.enabled,
                    isSelected = index == selectedAnswerIndex,
                    onClick = { onAnswerSelected(index) },
                    modifier = Modifier.testTag(index.toString())
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(top = 6.dp),
        measurePolicy = { measurables, constraints ->
            // Medimos los elementos hijos
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            // Calculamos el radio del arco (usamos un porcentaje del ancho total)
            val radius = constraints.maxWidth * 0.45f

            // Calculamos el ángulo de inicio y fin del arco (en radianes)
            val startAngle = 0.0
            val endAngle = Math.PI

            // Calculamos el ángulo de separación entre los elementos
            val angleStep = (startAngle - endAngle) / (placeables.size - 1)

            // Calculamos el centro de la pantalla
            val centerX = constraints.maxWidth / 2f
            val centerY = constraints.maxHeight / 2f

            // Calculamos la posición de cada elemento en el arco
            var currentAngle = startAngle
            val positions = mutableListOf<Offset>()
            placeables.forEachIndexed { index, placeable ->
                val x = centerX + radius * cos(currentAngle).toFloat() - placeable.width / 2f
                val y = centerY - radius * sin(currentAngle).toFloat() - placeable.height / 2f
                positions.add(
                    Offset(
                        if (index == 1) x / 1.4f else if (index == 3) x / 1.4f else x,
                        y / 4.7f
                    )
                )
                currentAngle -= angleStep
            }

            // Devolvemos las medidas de los hijos con sus posiciones
            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight,
                alignmentLines = emptyMap()
            ) {
                val reversedPlaceables = placeables.reversed()
                positions.forEachIndexed { index, position ->
                    reversedPlaceables[index].placeRelative(
                        position.x.toInt(),
                        position.y.toInt()
                    )
                }
            }
        }
    )
}

@Composable
private fun ZEMTAnswerIcon(
    @DrawableRes unselectedIcon: Int,
    @DrawableRes selectedIcon: Int,
    isEnabled: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.5f else 1f,
        label = "answer icon scale"
    )

    Box(
        modifier = modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .scale(scale)
                .shadow(10.dp, CircleShape, false)
                .clip(CircleShape)
                .background(Color.White)
                .clip(CircleShape)
                .clickable(enabled = isEnabled) {
                    onClick.invoke()
                }
        ) {
            Image(
                painter = painterResource(id = if (isSelected) selectedIcon else unselectedIcon),
                contentDescription = null,
                colorFilter = if (isEnabled) {
                    null
                } else {
                    ColorFilter.tint(colorResource(id = RDS.color.zcds_light_gray_300))
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(if (isSelected) 4.dp else 2.dp)
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_XL,
    showSystemUi = true
)
@Composable
private fun ZEMTAnswerListPreview() {
    ZEMTAnswerList(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        onAnswerSelected = {},
        onAnswerPreselected = {},
        answers = ZEMTDiscoverMockData.mockAnswers
    )
}