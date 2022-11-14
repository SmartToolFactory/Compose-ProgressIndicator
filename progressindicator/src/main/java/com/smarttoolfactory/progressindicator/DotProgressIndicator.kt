package com.smarttoolfactory.progressindicator

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.progressindicator.util.scale

/**
 * Bouncing dot progress indicator that draws 3 dots that constantly bouncing after the dot on its
 * left.
 * If [initialColor] and [animatedColor] are not same colors color is animated
 * from [initialColor] to [animatedColor]
 *
 * @param initialColor color that is initially set with animation
 * @param animatedColor color that is set after animation
 */
@Composable
fun BouncingDotProgressIndicator(
    modifier: Modifier = Modifier,
    initialColor: Color = IndicatorDefaults.DotColor,
    animatedColor: Color = IndicatorDefaults.DotColor
) {
    val dotAnimatables = remember {
        listOf(
            Animatable(0f),
            Animatable(0f),
            Animatable(0f)
        )
    }
    dotAnimatables.forEachIndexed { index, animatable ->

        LaunchedEffect(key1 = animatable) {
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    initialStartOffset = StartOffset(index * 150),
                    animation = keyframes {
                        durationMillis = 1000
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0f at 700 with LinearOutSlowInEasing
                        0f at 1000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val sameColor = initialColor == animatedColor

    Canvas(
        modifier
            .progressSemantics()
            .size(Size * 2, Size)
            .padding(8.dp)
    ) {
        val canvasWidth = size.width

        val space = canvasWidth * 0.1f
        val diameter = (canvasWidth - 2 * space) / 3
        val radius = diameter / 2

        dotAnimatables.forEachIndexed { index, animatable ->
            val x = radius + index * (diameter + space)
            val value = animatable.value

            drawCircle(
                color = if (sameColor) initialColor else lerp(
                    initialColor,
                    animatedColor,
                    value
                ),
                center = Offset(
                    x = x,
                    y = center.y - radius * value * 1.6f
                ),
                radius = radius
            )
        }
    }
}

/**
 * Dot progress indicator that draws 3 dots that constantly changing radius after the one
 * next to it. If [initialColor] and [animatedColor] are not same colors color is animated
 * from [initialColor] to [animatedColor]
 *
 * @param initialColor color that is initially set with animation
 * @param animatedColor color that is set after animation
 */
@Composable
fun DotProgressIndicator(
    modifier: Modifier = Modifier,
    initialColor: Color = IndicatorDefaults.DotColor,
    animatedColor: Color = IndicatorDefaults.DotColor
) {

    val initialValue = 0.25f

    val dotAnimatables = remember {
        listOf(
            Animatable(initialValue),
            Animatable(initialValue),
            Animatable(initialValue)
        )
    }
    dotAnimatables.forEachIndexed { index, animatable ->

        LaunchedEffect(key1 = animatable) {
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    initialStartOffset = StartOffset(index * 300),
                    animation = tween(600, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            )
        }
    }

    val sameColor = initialColor == animatedColor

    Canvas(
        modifier
            .progressSemantics()
            .size(Size * 2, Size)
            .padding(8.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val diameter = (canvasHeight / 2).coerceAtLeast(canvasWidth / 3)
        val radius = diameter / 2

        dotAnimatables.forEachIndexed { index, animatable ->
            val x = radius + index * (diameter)
            val value = animatable.value
            val colorFraction =
                scale(start1 = initialValue, end1 = 1f, pos = value, start2 = 0f, end2 = 1f)

            drawCircle(
                color = if (sameColor) initialColor.copy(alpha = value) else
                    lerp(
                        initialColor,
                        animatedColor,
                        colorFraction
                    ),
                center = Offset(
                    x = x,
                    y = center.y
                ),
                radius = radius * value
            )
        }
    }
}