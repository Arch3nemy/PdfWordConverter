package com.alacrity.music.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alacrity.music.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_semibold, FontWeight.W600)
)

private val Domine = FontFamily(
    Font(R.font.domine_regular),
    Font(R.font.domine_bold, FontWeight.Bold)
)

val TemplateTypography = Typography(
    defaultFontFamily = Montserrat,
    h1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 1.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = 1.sp
    ),
)
