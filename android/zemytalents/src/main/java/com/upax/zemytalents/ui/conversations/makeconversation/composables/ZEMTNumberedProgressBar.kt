package com.upax.zemytalents.ui.conversations.makeconversation.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
fun ZEMTNumberedProgressBar(
    numbers: List<Int>,
    currentIndex: Int,
    isSelected: Boolean,
    unselectedColor: Color,
    currentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in numbers.indices) {
            val isCurrent = i == currentIndex
            val isPrevious = i < currentIndex

            val circleBackgroundColor = when {
                isPrevious -> currentColor
                isCurrent && isSelected -> currentColor
                else -> Color.Transparent
            }

            val circleBorderColor = when {
                isPrevious -> currentColor
                isCurrent -> currentColor
                else -> unselectedColor
            }

            val textColor = when {
                isPrevious -> Color.White
                isCurrent && isSelected -> Color.White
                isCurrent -> currentColor
                else -> unselectedColor
            }

            val targetBoxSize: Dp = if (isCurrent) 32.dp else 24.dp
            val boxSize: Dp by animateDpAsState(targetValue = targetBoxSize, label = "")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(boxSize)
                    .background(color = circleBackgroundColor, shape = CircleShape)
                    .border(width = 2.dp, color = circleBorderColor, shape = CircleShape)
            ) {
                ZEMTText(
                    text = numbers[i].toString(),
                    color = textColor.toArgb(),
                    style = DesignSystem.style.TextAppearance_ZCDSApp_BodySmallBlack,
                    fontSize = if (isCurrent) 13.33.sp else 12.sp,
                    modifier = Modifier
                )
            }

            if (i < numbers.lastIndex) {
                val lineColor = if (i < currentIndex) currentColor else unselectedColor
                Spacer(
                    modifier = Modifier
                        .weight(1f, true)
                        .height(2.dp)
                        .background(color = lineColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NumberedProgressBarPreview() {
    ZEMTNumberedProgressBar(
        numbers = listOf(1, 2, 3, 4),
        currentIndex = 0,
        unselectedColor = Color.Gray,
        currentColor = Color.Blue,
        modifier = Modifier,
        isSelected = false
    )
}

@Preview(showBackground = true)
@Composable
private fun NumberedProgressBarPreview2() {
    ZEMTNumberedProgressBar(
        numbers = listOf(1, 2, 3, 4),
        currentIndex = 0,
        unselectedColor = Color.Gray,
        currentColor = Color.Blue,
        modifier = Modifier,
        isSelected = true
    )
}

@Preview(showBackground = true)
@Composable
private fun NumberedProgressBarPreview3() {
    ZEMTNumberedProgressBar(
        numbers = listOf(1, 2, 3, 4),
        currentIndex = 2,
        unselectedColor = Color.Gray,
        currentColor = Color.Blue,
        modifier = Modifier,
        isSelected = false
    )
}

@Preview(showBackground = true)
@Composable
private fun NumberedProgressBarPreview4() {
    ZEMTNumberedProgressBar(
        numbers = listOf(1, 2, 3, 4),
        currentIndex = 2,
        unselectedColor = Color.Gray,
        currentColor = Color.Blue,
        modifier = Modifier,
        isSelected = true
    )
}
