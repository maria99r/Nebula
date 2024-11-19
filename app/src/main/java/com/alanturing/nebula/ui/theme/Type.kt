package com.alanturing.nebula.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val AppTypography = Typography(
    // titulo grande
    titleLarge = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                letterSpacing = 0.5.sp,
            )
            ),
    // titulo grande
    titleMedium = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 30.sp,
                letterSpacing = 0.5.sp,
            )
            ),
    // texto normal
    bodyMedium = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 24.sp
            )
            ),
    // texto pequeño
    labelSmall = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp
            )
            ),
)


