package com.example.examplemvvm.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

enum class AlertaTipo { EXITO, ERROR, INFO }

@Composable
fun AlertaSnackBar(
    message: String,
    type: AlertaTipo,
    onDismiss: () -> Unit,
    modifier: Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val backgroundColor = when (type) {
        AlertaTipo.EXITO -> Color(0xFF4CAF50)
        AlertaTipo.ERROR -> Color(0xFFF44336)
        AlertaTipo.INFO -> Color(0xFFB5A047)
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message)
            onDismiss()
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                snackbarData = data,
                containerColor = backgroundColor,
                contentColor = Color.White
            )
        }
    )
}
