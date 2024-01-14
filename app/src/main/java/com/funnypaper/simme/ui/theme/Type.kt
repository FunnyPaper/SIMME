package com.funnypaper.simme.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.funnypaper.simme.R

val jura_regular = Font(
    resId = R.font.jura_regular,
    weight = FontWeight.Normal
)

val jura_bold = Font(
    resId = R.font.jura_bold,
    weight = FontWeight.Bold
)

val jura_light = Font(
    resId = R.font.jura_light,
    weight = FontWeight.Light
)

val jura_medium = Font(
    resId = R.font.jura_medium,
    weight = FontWeight.Medium
)

val jura_semibold = Font(
    resId = R.font.jura_semibold,
    weight = FontWeight.SemiBold
)

val jura = FontFamily(
    jura_light,
    jura_regular,
    jura_medium,
    jura_semibold,
    jura_bold
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = jura,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 32.sp * 1.25,
        letterSpacing = 0.sp,
    )
)