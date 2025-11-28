package com.example.examplemvvm.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = TealDeep,
    onPrimary = Color.White,
    primaryContainer = BlueMedium,
    secondary = GreenSoft,
    onSecondary = Color.White,
    secondaryContainer = GreenPastel,
    background = BackgroundLight,
    onBackground = OnDark,
    surface = SurfaceLight,
    onSurface = OnDark,
    errorContainer = OnError
)

@Composable
fun ExampleMVVMTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}

