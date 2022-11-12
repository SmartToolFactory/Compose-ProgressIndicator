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
import com.smarttoolfactory.progressindicator.ui.theme.DynamicItemColor
import com.smarttoolfactory.progressindicator.ui.theme.StaticItemColor


@Composable
fun SpinningCircleProgressIndicator(
    modifier: Modifier = Modifier,
    staticItemColor: Color = StaticItemColor,
    dynamicItemColor: Color = DynamicItemColor,
    durationInMillis: Int = 1000
) {
    val count = 8

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = count.toFloat(), animationSpec = infiniteRepeatable(
            animation = tween(durationInMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(48.dp)) {

        val canvasWidth = size.width
        val canvasHeight = size.height

        val radius = canvasWidth * .12f

        // Stationary items
        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                drawCircle(
                    color = staticItemColor,
                    radius = radius,
                    center = Offset(canvasWidth - radius, canvasHeight / 2),
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
                    center = Offset(canvasWidth - radius, canvasHeight / 2),
                )
            }
        }
    }
}

@Composable
fun ScaledCircleProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val count = 8f

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1100
                count * 0.8f at 700
                count * 0.1f at 150 with FastOutLinearInEasing
                count * 0.10f at 150
            }
        )
    )

    val radiusList = remember { arrayListOf<Float>() }

    Canvas(modifier = modifier.size(48.dp)) {

        val canvasWidth = size.width
        val canvasHeight = size.height

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


        // Dynamic items
        for (i in 0 until radiusList.size) {

            val radius = radiusList[i]

            rotate((angle + i) * coefficient) {
                drawCircle(
                    color = color.copy(alpha = ( i * .2f).coerceIn(0f, 1f)),
                    radius = radius,
                    center = Offset(radiusDynamicContainer, canvasHeight / 2),
                )
            }
        }
    }
}