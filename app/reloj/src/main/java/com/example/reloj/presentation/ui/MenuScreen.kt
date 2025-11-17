package com.example.reloj.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

        Button(
            onClick = { navController.navigate("estado") }
        ) {
            Text("Registrar estado")
        }

        Spacer(Modifier.height(15.dp))

        Button(
            onClick = { navController.navigate("recomendacion") }
        ) {
            Text("Ver recomendación")
        }
    }
}
