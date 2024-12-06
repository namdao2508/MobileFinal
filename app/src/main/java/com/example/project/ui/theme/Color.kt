package com.example.project.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Define custom light theme colors
val LightColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE),
    primaryContainer = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFf0f0f0),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    onPrimary = Color.White,
    onBackground = Color.Black
)

// Define custom dark theme colors
val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFBB86FC),
    primaryContainer = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1F1F1F),
    onSurface = Color.White,
    onPrimary = Color.Black,
    onBackground = Color.White
)
