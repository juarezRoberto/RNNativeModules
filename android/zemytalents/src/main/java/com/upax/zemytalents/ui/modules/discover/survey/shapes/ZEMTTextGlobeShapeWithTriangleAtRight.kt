package com.upax.zemytalents.ui.modules.discover.survey.shapes

import android.content.res.Resources
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class ZEMTTextGlobeShapeWithTriangleAtRight(
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
            val triangleHeightPx = triangleHeight.toPx()
            val rectangleWidth = width.toPx()

            val triangleRadius = triangleHeightPx / 2
            val triangleLeftPosition = rectangleWidth - triangleRadius * 2
            val triangleTopVertex = 0f

            val trianglePath = Path().apply {
                moveTo(triangleLeftPosition, triangleHeightPx)
                lineTo(rectangleWidth - triangleRadius, triangleTopVertex)
                lineTo(rectangleWidth, triangleHeightPx)
                close()
            }
            addPath(path = trianglePath, offset = Offset.Zero)

            val squareStart = 0f
            val cornerRadius = 8.dp.toPx()
            val rectangleHeight = textContainerHeight.toPx()

            val roundedRect = Path().apply {
                moveTo(squareStart + cornerRadius, triangleHeightPx)

                lineTo(rectangleWidth, triangleHeightPx)

                lineTo(rectangleWidth, triangleHeightPx + rectangleHeight - cornerRadius)

                arcTo(
                    rect = Rect(
                        left = rectangleWidth - cornerRadius * 2,
                        top = triangleHeightPx + rectangleHeight - cornerRadius * 2,
                        right = rectangleWidth,
                        bottom = triangleHeightPx + rectangleHeight
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                lineTo(squareStart + cornerRadius, triangleHeightPx + rectangleHeight)

                arcTo(
                    rect = Rect(
                        left = squareStart,
                        top = triangleHeightPx + rectangleHeight - cornerRadius * 2,
                        right = squareStart + cornerRadius * 2,
                        bottom = triangleHeightPx + rectangleHeight
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                lineTo(squareStart, triangleHeightPx + cornerRadius)

                arcTo(
                    rect = Rect(
                        left = squareStart,
                        top = triangleHeightPx,
                        right = squareStart + cornerRadius * 2,
                        bottom = triangleHeightPx + cornerRadius * 2
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                close()
            }
            addPath(roundedRect, offset = Offset.Zero)
        }
        return Outline.Generic(path = trianglePath)
    }

    private fun Dp.toPx(): Float = value * Resources.getSystem().displayMetrics.density
}