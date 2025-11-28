package com.example.reloj.presentation.ui

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.navigation.composable

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()

    MaterialTheme {
        AppScaffold(
            timeText = { TimeText() }    // ← SLOT NECESARIO
        ) {                             // ← ESTE ES EL CONTENT SLOT
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = "menu"
            ) {
                composable("menu") {
                    MenuPrincipalScreen(navController)
                }
                composable("estado") {
                    EstadoAnimoScreen()
                }
                composable("recomendacion") {
                    SugerenciasScreen()
                }
            }
        }
    }
}

/*
*   val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "menu") {
            composable("estado") {
                EstadoAnimoScreen { emocion ->
                    // Aquí puedes enviar la emoción al teléfono o guardar localmente
                    // navController.navigate("confirmacion")
                }
            }
            composable("menu") {
                MenuPrincipalScreen(navController)
            }
            composable("recomendacion") {

            }

            // composable("confirmacion") { ConfirmacionScreen() }
        }*/