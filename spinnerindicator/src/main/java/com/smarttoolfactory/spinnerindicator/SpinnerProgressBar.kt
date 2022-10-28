package com.smarttoolfactory.spinnerindicator

import androidx.compose.animation.*
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpinningProgressBar(
    modifier: Modifier = Modifier,
    show: Boolean = true
) {

    val count = 12

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(count * 60, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val backgroundColor = Color.DarkGray
    val foregroundColor = Color(0xff9E9E9E)

    AnimatedVisibility(
        visible = show,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Canvas(modifier = modifier.size(48.dp)) {

            val canvasWidth = size.width
            val canvasHeight = size.height

            val width = canvasWidth * .3f
            val height = canvasHeight / 10

            val cornerRadius = width.coerceAtMost(height) / 2

            // Stationary items
            for (i in 0..360 step 360 / count) {
                rotate(i.toFloat()) {
                    drawRoundRect(
                        color = backgroundColor,
                        topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }

            val coefficient = 360f / count

            // Dynamic items
            for (i in 0..count / 2) {
                rotate((angle.toInt() + i) * coefficient) {
                    drawRoundRect(
                        color = foregroundColor.copy(alpha = (0.2f + 0.15f * i).coerceIn(0f, 1f)),
                        topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpinningCircleProgressBar(
    modifier: Modifier = Modifier,
    show: Boolean = true
) {

    val count = 8

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(count * 120, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val backgroundColor = Color.DarkGray
    val foregroundColor = Color(0xff9E9E9E)

    AnimatedVisibility(
        visible = show,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Canvas(modifier = modifier.size(48.dp)) {

            val canvasWidth = size.width
            val canvasHeight = size.height

            val radius = canvasWidth * .1f

            // Stationary items
            for (i in 0..360 step 360 / count) {
                rotate(i.toFloat()) {
                    drawCircle(
                        color = backgroundColor,
                        radius = radius,
                        center = Offset(canvasWidth - radius, canvasHeight / 2),
                    )
                }
            }

            val coefficient = 360f / count

            // Dynamic items
            for (i in 0..count) {
                rotate((angle.toInt() + i) * coefficient) {
                    drawCircle(
                        color = foregroundColor.copy(alpha = (  i.toFloat()/count).coerceIn(0f, 1f)),
                        radius = radius,
                        center = Offset(canvasWidth - radius, canvasHeight / 2),
                    )
                }
            }
        }
    }
}
