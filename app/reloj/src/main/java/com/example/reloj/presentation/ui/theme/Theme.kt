package com.example.examplemvvm.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material3.dynamicColorScheme
import com.example.android.wearable.composestarter.presentation.theme.Typography
import com.example.android.wearable.composestarter.presentation.theme.wearColorScheme
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun WearTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = dynamicColorScheme(LocalContext.current) ?: wearColorScheme,
        typography = Typography,
        // For shapes, we generally recommend using the default Material Wear shapes which are
        // optimized for round and non-round devices.
        content = content
    )
}

