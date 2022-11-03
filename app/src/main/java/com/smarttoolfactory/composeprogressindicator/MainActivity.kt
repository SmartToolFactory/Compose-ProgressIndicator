@file:OptIn(ExperimentalMaterial3Api::class)

package com.smarttoolfactory.composeprogressindicator

import android.graphics.ComposePathEffect
import android.graphics.CornerPathEffect
import android.graphics.DiscretePathEffect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeprogressindicator.ui.theme.composeprogressindicatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            composeprogressindicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column {
                        Box(
                            modifier = Modifier
//                                .background(Color.Black)
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
//                            SpinningProgressIndicator(show = true)
//                            SpinningCircleProgressIndicator(show = true)
//                            GooeyEffect()
                            GooeyEffectSample2()
//                            NeonSample()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GooeyEffectSample2() {

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

    val modifier = Modifier
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

@Composable
private fun GooeyEffect() {

    val pathLeft = remember { Path() }
    val pathRight = remember { Path() }

    val segmentCount = 20
    val pathMeasure = PathMeasure()
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = size.center

        if (pathLeft.isEmpty) {
            pathLeft.addOval(
                Rect(
                    center = Offset(center.x - 100f, center.y),
                    radius = 200f
                )
            )
        }

        if (pathRight.isEmpty) {
            pathRight.addOval(
                Rect(
                    center = Offset(center.x + 100f, center.y),
                    radius = 200f
                )
            )
        }


        val path = Path.combine(PathOperation.Union, pathLeft, pathRight)
        pathMeasure.setPath(pathLeft, true)

        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        val cornerPathEffect = CornerPathEffect(50f)

        val composePathEffect = ComposePathEffect(cornerPathEffect, discretePathEffect)


        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect.toComposePathEffect(),
            inner = discretePathEffect.toComposePathEffect()
        )
        drawPath(path, Color.Blue, style = Stroke(4.dp.toPx()))

        translate(top = 50f) {
            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(
                    4.dp.toPx(),
                    pathEffect = chainPathEffect
                )
            )
        }

        translate(top = 100f) {
            drawPath(
                path = path,
                color = Color.Cyan,
                style = Stroke(
                    4.dp.toPx(),
                    pathEffect = composePathEffect.toComposePathEffect()
                )
            )
        }
    }
}

@Composable
private fun NeonSample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 30f
            }
        }

        val frameworkPaint = remember {
            paint.asFrameworkPaint()
        }

        val color = Color.Red


        Canvas(modifier = Modifier.fillMaxSize()) {
            this.drawIntoCanvas {

                val transparent = color
                    .copy(alpha = 0f)
                    .toArgb()

                frameworkPaint.color = transparent

                frameworkPaint.setShadowLayer(
                    10f,
                    0f,
                    0f,
                    color
                        .copy(alpha = .5f)
                        .toArgb()
                )

                it.drawRoundRect(
                    left = 100f,
                    top = 100f,
                    right = 500f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(100f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
//                    style = Stroke(width = 2.dp.toPx())
                )


                frameworkPaint.setShadowLayer(
                    130f,
                    0f,
                    0f,
                    color
                        .copy(alpha = .5f)
                        .toArgb()
                )


                it.drawRoundRect(
                    left = 600f,
                    top = 100f,
                    right = 1000f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(600f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}