package com.smarttoolfactory.composeprogressindicator

import android.graphics.CornerPathEffect
import android.graphics.DiscretePathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


@Composable
fun GooeyEffectSampleStroke(
    modifier: Modifier = Modifier
) {

    val segmentCount = 50
    val pathMeasure = remember {
        PathMeasure()
    }

    val pathDynamic = remember { Path() }
    val pathStatic = remember { Path() }

    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Green)
        )
    }


    /**
     * Current position of the pointer that is pressed or being moved
     */
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }


    val drawModifier = modifier
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                currentPosition = change.position
            }
        }
        .fillMaxSize()


    Canvas(modifier = drawModifier) {

        val center = size.center

        val position = if (currentPosition == Offset.Unspecified) {
            center
        } else currentPosition

        pathDynamic.reset()
        pathDynamic.addOval(
            Rect(
                center = position,
                radius = 150f
            )
        )

        pathStatic.reset()
        pathStatic.addOval(
            Rect(
                center = Offset(center.x, center.y),
                radius = 100f
            )
        )

        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        val cornerPathEffect = PathEffect.cornerPathEffect(50f)


        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect,
            inner = discretePathEffect.toComposePathEffect()
        )

        pathDynamic.op(pathDynamic, pathStatic, PathOperation.Union)
        pathMeasure.setPath(pathDynamic, true)

        drawPath(
            path = pathDynamic,
            brush = brush,
            style = Stroke(4.dp.toPx(), pathEffect = chainPathEffect)
        )

    }

}

@Composable
fun GooeyEffectSampleFilled(
    modifier: Modifier = Modifier
) {

    val pathDynamic = remember { Path() }
    val pathStatic = remember { Path() }

    /**
     * Current position of the pointer that is pressed or being moved
     */
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val segmentCount = 20
    val pathMeasure = remember {
        PathMeasure()
    }

    modifier
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                currentPosition = change.position
            }
        }
        .fillMaxSize()

    val paint = remember {
        Paint()
    }

    var isPaintSetUp by remember {
        mutableStateOf(false)
    }

    Canvas(modifier = modifier) {
        val center = size.center

        val position = if (currentPosition == Offset.Unspecified) {
            center
        } else currentPosition

        pathDynamic.reset()
        pathDynamic.addOval(
            Rect(
                center = position,
                radius = 150f
            )
        )

        pathStatic.reset()
        pathStatic.addOval(
            Rect(
                center = Offset(center.x, center.y),
                radius = 100f
            )
        )

        pathMeasure.setPath(pathDynamic, true)

        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        val cornerPathEffect = CornerPathEffect(50f)


        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect.toComposePathEffect(),
            inner = discretePathEffect.toComposePathEffect()
        )

        if (!isPaintSetUp) {

            paint.shader = LinearGradientShader(
                from = Offset.Zero,
                to = Offset(size.width, size.height),
                colors = listOf(
                    Color(0xffFFEB3B),
                    Color(0xffE91E63)
                ),
                tileMode = TileMode.Clamp
            )
            isPaintSetUp = true
            paint.pathEffect = chainPathEffect
        }

        val newPath = Path.combine(PathOperation.Union, pathDynamic, pathStatic)

        with(drawContext.canvas) {
            this.drawPath(
                newPath,
                paint
            )
        }
    }
}