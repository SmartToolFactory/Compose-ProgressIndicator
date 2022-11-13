package com.smarttoolfactory.progressindicator

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.smarttoolfactory.progressindicator.IndicatorDefaults.DynamicItemColor
import com.smarttoolfactory.progressindicator.IndicatorDefaults.Size
import com.smarttoolfactory.progressindicator.IndicatorDefaults.StaticItemColor

/**
 * Indeterminate Material Design spinning progress indicator with rectangle or rounded rectangle
 * shape.
 * @param staticItemColor color of the spinning items
 * @param dynamicItemColor color of the stationary items
 * @param spinnerShape shape of the items whether [SpinnerShape.Rect] or [SpinnerShape.RoundedRect]
 * @param durationMillis duration of one cycle of spinning
 */
@Composable
fun SpinningProgressIndicator(
    modifier: Modifier = Modifier,
    staticItemColor: Color = StaticItemColor,
    dynamicItemColor: Color = DynamicItemColor,
    spinnerShape: SpinnerShape = SpinnerShape.RoundedRect,
    durationMillis: Int = 1000
) {

    val count = 12
    val coefficient = 360f / count

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier
        .progressSemantics()
        .size(Size)
    ) {

        var canvasWidth = size.width
        var canvasHeight = size.height

        if (canvasHeight < canvasWidth) {
            canvasWidth = canvasHeight
        } else {
            canvasHeight = canvasWidth
        }

        val itemWidth = canvasWidth * .3f
        val itemHeight = canvasHeight / 12

        val cornerRadius = itemWidth.coerceAtMost(itemHeight) / 2

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2

        val topLeftOffset = Offset(
            x = horizontalOffset + canvasWidth - itemWidth,
            y = verticalOffset + (canvasHeight - itemHeight) / 2
        )

        val size = Size(itemWidth, itemHeight)

        // Stationary items
        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                if (spinnerShape == SpinnerShape.RoundedRect) {
                    drawRoundRect(
                        color = staticItemColor,
                        topLeft = topLeftOffset,
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                } else {
                    drawRect(
                        color = staticItemColor,
                        topLeft = topLeftOffset,
                        size = size,
                    )
                }
            }
        }

        // Dynamic items
        for (i in 0..count / 2) {
            rotate((angle.toInt() + i) * coefficient) {
                if (spinnerShape == SpinnerShape.RoundedRect) {
                    drawRoundRect(
                        color = dynamicItemColor.copy(alpha = (0.2f + 0.15f * i).coerceIn(0f, 1f)),
                        topLeft = topLeftOffset,
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                } else {
                    drawRect(
                        color = dynamicItemColor.copy(
                            alpha = (0.2f + 0.15f * i).coerceIn(
                                0f, 1f
                            )
                        ),
                        topLeft = topLeftOffset,
                        size = size,
                    )
                }
            }
        }
    }
}
