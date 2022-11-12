package com.smarttoolfactory.progressindicator

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object IndicatorDefaults {
    val Size = 48.0.dp
    val StaticItemColor = Color(0xff757575)
    val DynamicItemColor = Color(0xffEEEEEE)
}

enum class SpinnerShape {
    Rect, RoundedRect
}
