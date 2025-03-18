package com.upax.zemytalents.ui.modules.discover.survey.shapes

import android.content.res.Resources
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Path.Direction
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class ZEMTTextGlobeShapeWithTriangleInMiddle(
    private val width: Dp,
    private val textContainerHeight: Dp,
    private val triangleHeight: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            val middleScreen = size.width / 2
            val triangleHeightPx = triangleHeight.toPx()

            val triangleRadius = triangleHeightPx / 2
            val triangleLeftVertex = middleScreen - triangleRadius
            val triangleRightVertex = middleScreen + triangleRadius
            val triangleTopVertex = 0f

            val trianglePath = Path().apply {
                moveTo(triangleLeftVertex, triangleHeightPx)
                lineTo(middleScreen, triangleTopVertex)
                lineTo(triangleRightVertex, triangleHeightPx)
                close()
            }
            addPath(
                path = trianglePath,
                offset =  Offset.Zero
            )
            val squareStart = 0f
            val cornerRadius = 8.dp.toPx()
            val rectangleWidth = width.toPx()
            val rectangleHeight = textContainerHeight.toPx()


            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = Offset(squareStart, triangleHeightPx),
                        size = Size(rectangleWidth, rectangleHeight)
                    ),
                    cornerRadius = CornerRadius(cornerRadius)
                ),
                direction = Direction.CounterClockwise
            )
        }
        return Outline.Generic(path = trianglePath)
    }

    private fun Dp.toPx(): Float = value * Resources.getSystem().displayMetrics.density
}