package com.nhdeveloper.nhfiles.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFA726),
    onPrimary = Color(0xFF1A1100),
    primaryContainer = Color(0xFF3B2A0A),
    onPrimaryContainer = Color(0xFFFFD79A),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFF1A1100),
    background = Color(0xFF0D0F12),
    onBackground = Color(0xFFE8EAED),
    surface = Color(0xFF14171B),
    onSurface = Color(0xFFE8EAED),
    surfaceVariant = Color(0xFF1D2126),
    onSurfaceVariant = Color(0xFF9AA0A6),
    outline = Color(0xFF3A3F45),
    outlineVariant = Color(0xFF2A2E33),
    error = Color(0xFFFF6B6B),
    surfaceContainerLowest = Color(0xFF0A0C0F),
    surfaceContainerLow = Color(0xFF111418),
    surfaceContainer = Color(0xFF161A1F),
    surfaceContainerHigh = Color(0xFF1B1F25),
    surfaceContainerHighest = Color(0xFF21262C),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFFE08600),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFE3B8),
    onPrimaryContainer = Color(0xFF3B2400),
    secondary = Color(0xFFB36B00),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF6F6F4),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFEDEDEA),
    onSurfaceVariant = Color(0xFF5F6368),
    outline = Color(0xFFB8B8B4),
    outlineVariant = Color(0xFFDDDDD8),
    error = Color(0xFFD32F2F),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F6),
    surfaceContainer = Color(0xFFF1F1EE),
    surfaceContainerHigh = Color(0xFFEBEBE8),
    surfaceContainerHighest = Color(0xFFE5E5E1),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

private val AppTypography = Typography(
    headlineSmall = TextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.SemiBold),
    titleLarge = TextStyle(fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium),
    titleSmall = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 17.sp),
    labelLarge = TextStyle(fontSize = 13.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 11.sp, lineHeight = 15.sp, fontWeight = FontWeight.Medium),
)

@Composable
fun NHFilesTheme(mode: Int, content: @Composable () -> Unit) {
    val dark = when (mode) {
        1 -> false
        2 -> true
        else -> isSystemInDarkTheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !dark
                controller.isAppearanceLightNavigationBars = !dark
            }
        }
    }
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
