package com.example.reloj.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun MenuPrincipalScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para registrar estado
        Button(
            onClick = { navController.navigate("estado") },
            colors = ButtonDefaults.buttonColors(Color(0xFFB2D8B5))
        ) {
            Text("Registrar estado", style = MaterialTheme.typography.bodyLarge,color = Color.Black)
        }

        Spacer(Modifier.height(5.dp))

        // Botón para ver recomendaciones
        Button(
            onClick = { navController.navigate("recomendacion") },
            colors = ButtonDefaults.buttonColors(Color(0xFFB2D8B5))
        ) {
            Text("Ver recomendación", style = MaterialTheme.typography.bodyLarge,color = Color.Black)
        }
    }
}