package com.alacrity.music.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)

val ColorDefaultBackground = Color(0xFF17B284)

val ColorDefaultBackground1 = Color(0xFF004953)
val ColorDefaultBackground2 = Color(0xFF003A44)
val ColorDefaultBackground3 = Color(0xFF002B35)
val ColorDefaultBackground4 = Color(0xFF0F5862)

val gradientBackgroundBrush = Brush.verticalGradient(
    colors = listOf(
        ColorDefaultBackground1,
        ColorDefaultBackground2,
        ColorDefaultBackground3,
        ColorDefaultBackground4,
    )
)

val ColorButtonDefault = Color(0xFF0F4392)
