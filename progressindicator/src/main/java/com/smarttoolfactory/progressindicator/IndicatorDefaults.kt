package com.smarttoolfactory.progressindicator

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object IndicatorDefaults {
    val Size = 48.0.dp
    val StaticItemColor = Color(0xff757575)
    val DynamicItemColor = Color(0xffEEEEEE)

    val DefaultGradientColors = listOf(
        Color(0xffFFEB3B),
        Color(0xffE91E63)
    )
}

enum class SpinnerShape {
    Rect, RoundedRect
}

enum class IndicatorStyle {
    Stroke, Filled
}