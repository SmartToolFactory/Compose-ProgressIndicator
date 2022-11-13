package com.smarttoolfactory.progressindicator

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.progressindicator.IndicatorDefaults.DynamicItemColor
import com.smarttoolfactory.progressindicator.IndicatorDefaults.StaticItemColor

/**
 * Indeterminate Material Design spinning progress indicator with circle items
 * shape.
 * @param staticItemColor color of the spinning items
 * @param dynamicItemColor color of the stationary items
 * @param durationMillis duration of one cycle of spinning
 */
@Composable
fun SpinningCircleProgressIndicator(
    modifier: Modifier = Modifier,
    staticItemColor: Color = StaticItemColor,
    dynamicItemColor: Color = DynamicItemColor,
    durationMillis: Int = 1000
) {
    val count = 8

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier
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

        val radius = canvasWidth * .12f

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2
        val center = Offset(
            x = horizontalOffset + canvasWidth - radius,
            y = verticalOffset + canvasHeight / 2
        )

        // Stationary items
        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                drawCircle(
                    color = staticItemColor,
                    radius = radius,
                    center = center,
                )
            }
        }

        val coefficient = 360f / count

        // Dynamic items
        for (i in 0..3) {
            rotate((angle.toInt() + i) * coefficient) {
                drawCircle(
                    color = dynamicItemColor.copy(
                        alpha = (i.toFloat() / 4).coerceIn(
                            0f, 1f
                        )
                    ),
                    radius = radius,
                    center = center,
                )
            }
        }
    }
}

/**
 * Indeterminate Material Design spinning progress indicator with circle items that are
 * with each item smaller than next
 * shape.
 * @param color color of the items
 * @param durationMillis duration of one cycle of spinning
 */
@Composable
fun ScaledCircleProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    durationMillis: Int = 1200
) {
    val count = 8f

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count,
        infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = durationMillis
                count * 0.9f at (durationMillis * 0.8f).toInt()
                count * 0.05f at (durationMillis * 0.1f).toInt() with FastOutLinearInEasing
                count * 0.05f at (durationMillis * 0.1f).toInt()
            }
        )
    )

    val radiusList = remember { arrayListOf<Float>() }

    Canvas(
        modifier
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

        val coefficient = 360f / count

        val radiusMax = canvasWidth / 2 * .1f

        if (radiusList.isEmpty()) {
            repeat(6) {
                if (it == 0) {
                    radiusList.add(radiusMax)
                } else {
                    radiusList.add(radiusList[it - 1] * 1.2f)
                }
            }
        }

        val radiusDynamicContainer = (canvasWidth - radiusList.last())

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2
        val center =
            Offset(horizontalOffset + radiusDynamicContainer, verticalOffset + canvasHeight / 2)

        // Dynamic items
        for (i in 0 until radiusList.size) {

            val radius = radiusList[i]

            rotate((angle + i) * coefficient) {
                drawCircle(
                    color = color.copy(alpha = (i * .2f).coerceIn(0f, 1f)),
                    radius = radius,
                    center = center,
                )
            }
        }
    }
}

internal val Size = 48.0.dp
