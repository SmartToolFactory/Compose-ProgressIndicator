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
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.progressindicator.ui.theme.DynamicItemColor
import com.smarttoolfactory.progressindicator.ui.theme.StaticItemColor

enum class SpinnerShape {
    Rect, RoundedRect
}

@Composable
fun SpinningProgressIndicator(
    modifier: Modifier = Modifier,
    staticItemColor: Color = StaticItemColor,
    dynamicItemColor: Color = DynamicItemColor,
    spinnerShape: SpinnerShape = SpinnerShape.RoundedRect,
    durationInMillis: Int = 1000
) {

    val count = 12

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationInMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )


    Canvas(modifier = modifier.size(48.dp)) {

        val canvasWidth = size.width
        val canvasHeight = size.height

        val width = canvasWidth * .3f
        val height = canvasHeight / 12

        val cornerRadius = width.coerceAtMost(height) / 2

        // Stationary items
        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                if (spinnerShape == SpinnerShape.RoundedRect) {
                    drawRoundRect(
                        color = staticItemColor,
                        topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                } else {
                    drawRect(
                        color = staticItemColor,
                        topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                        size = Size(width, height)
                    )
                }
            }
        }

            val coefficient = 360f / count

            // Dynamic items
            for (i in 0..count / 2) {
                rotate((angle.toInt() + i) * coefficient) {
                    if (spinnerShape == SpinnerShape.RoundedRect) {
                        drawRoundRect(
                            color = dynamicItemColor.copy(
                                alpha = (0.2f + 0.15f * i).coerceIn(
                                    0f, 1f
                                )
                            ),
                            topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                            size = Size(width, height),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                        )
                    } else {
                        drawRect(
                            color = dynamicItemColor.copy(
                                alpha = (0.2f + 0.15f * i).coerceIn(
                                    0f, 1f
                                )
                            ),
                            topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                            size = Size(width, height)
                        )
                    }
                }
            }
    }
}

