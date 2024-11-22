package com.alanturing.nebula.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.alanturing.nebula.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)


val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Lexend Deca"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    // titulo grande
    titleLarge =
    (
            androidx.compose.ui.text.TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                letterSpacing = 0.5.sp,
            )
            ),
    // titulo grande
    titleMedium = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 30.sp,
                letterSpacing = 0.5.sp,
            )
            ),
    // texto normal
    bodyMedium = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 24.sp
            )
            ),
    // texto pequeño
    labelSmall = (
            androidx.compose.ui.text.TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp
            )
            ),
)


