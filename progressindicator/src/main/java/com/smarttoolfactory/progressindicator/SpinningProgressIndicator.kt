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
 * @param staticItemCount number of stationary items. Set this value between 4 and 12 to have
 * better results. 8 or 12 are the best options to select to have indicators similar to ones
 * on ios/mac/web
 * @param staticItemColor color of the spinning items
 * @param dynamicItemColor color of the stationary items
 * @param spinnerShape shape of the items whether [SpinnerShape.Rect] or [SpinnerShape.RoundedRect]
 * @param durationMillis duration of one cycle of spinning
 */
@Composable
fun SpinningProgressIndicator(
    modifier: Modifier = Modifier,
    @androidx.annotation.IntRange(from = 4, to = 12) staticItemCount: Int = 12,
    dynamicItemCount: Int = staticItemCount / 2,
    staticItemColor: Color = StaticItemColor,
    dynamicItemColor: Color = DynamicItemColor,
    spinnerShape: SpinnerShape = SpinnerShape.RoundedRect,
    durationMillis: Int = 1000
) {

    // Number of rotating items
    val animatedItemCount = dynamicItemCount.coerceIn(1, staticItemCount)

    val coefficient = 360f / staticItemCount

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = staticItemCount.toFloat(),
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
        val itemHeight = canvasHeight / staticItemCount

        val cornerRadius = itemWidth.coerceAtMost(itemHeight) / 2

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2

        val topLeftOffset = Offset(
            x = horizontalOffset + canvasWidth - itemWidth,
            y = verticalOffset + (canvasHeight - itemHeight) / 2
        )

        val size = Size(itemWidth, itemHeight)

        // Stationary items
        for (i in 0..360 step 360 / staticItemCount) {
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
        for (i in 0..animatedItemCount) {
            // angle is cast to into move in intervals of static items
            rotate((angle.toInt() + i) * coefficient) {
                if (spinnerShape == SpinnerShape.RoundedRect) {
                    drawRoundRect(
                        color = dynamicItemColor.copy(
                            alpha = (1f / dynamicItemCount * i).coerceIn(0f, 1f)
                        ),
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
