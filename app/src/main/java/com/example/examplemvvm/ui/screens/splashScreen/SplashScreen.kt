package com.example.examplemvvm.ui.screens.splashScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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

    // Usamos Unit para que solo se ejecute una vez
    LaunchedEffect(Unit) {
        delay(1000)

        // Esperamos a que el estado esté disponible
        if (haySesion) {
            navController.navigate(Screens.DASHBOARD) {
                popUpTo(Screens.SPLASH) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.LOGIN) {
                popUpTo(Screens.SPLASH) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.respira1),
            contentDescription = "Logo"
        )
    }
}

