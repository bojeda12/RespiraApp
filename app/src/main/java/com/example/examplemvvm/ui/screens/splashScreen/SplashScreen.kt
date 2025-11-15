package com.example.examplemvvm.ui.screens.splashScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.examplemvvm.R
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val haySesion by viewModel.haySesion.collectAsState()

    LaunchedEffect(haySesion) {
        haySesion?.let { tieneSesion ->
            navController.navigate(
                if (tieneSesion) Screens.DASHBOARD else Screens.LOGIN
            ) {
                popUpTo(Screens.SPLASH) { inclusive = true }
            }
        }
    }

    // Pantalla invisible: sin contenido, sin fondo
    //Box(modifier = Modifier.fillMaxSize())
}



