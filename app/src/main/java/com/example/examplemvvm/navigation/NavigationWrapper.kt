package com.example.examplemvvm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examplemvvm.ui.screens.configuracion.Configuracion
import com.example.examplemvvm.ui.screens.configuracion.ConfiguracionScreen
import com.example.examplemvvm.ui.screens.dashboard.DashboardScreen
import com.example.examplemvvm.ui.screens.estado.EstadoScreen
import com.example.examplemvvm.ui.screens.historial.HistorialScreen

import com.example.examplemvvm.ui.screens.registro.RegistroScreen
import com.example.examplemvvm.ui.screens.respira.RespiraScreen
import com.example.examplemvvm.ui.screens.rutinas.RutinaScreen
import com.example.examplemvvm.ui.screens.splashScreen.SplashScreen
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginScreen


@Composable
fun NavigationWrapper() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SPLASH) {
        composable(Screens.LOGIN) {
            LoginScreen(navegarRegistro = {
                navController.navigate(Screens.REGISTRO) {
                    /*permite borrar el stack de navegacion y solo poner al que debemos volver*/
                    popUpTo(Screens.LOGIN) { inclusive = true }
                }
            }, navegarDashboard = {
                navController.navigate(Screens.DASHBOARD) {
                    /*permite borrar el stack de navegacion y solo poner al que debemos volver*/
                    popUpTo(Screens.LOGIN) { inclusive = true }
                }

            })
        }
        composable(Screens.REGISTRO) {
            RegistroScreen(
                navegarToDashboard = { navController.navigate(Screens.DASHBOARD){popUpTo(Screens.REGISTRO) { inclusive = true }} },
                navegarToLogin = { navController.navigate(Screens.LOGIN){popUpTo(Screens.LOGIN) { inclusive = true }} }
            )
        }
        composable(Screens.DASHBOARD) {
            DashboardScreen(
                navegarToConfiguracion={navController.navigate(Screens.CONFIGURACION)},
                navegarToEstados={navController.navigate(Screens.ESTADOANIMO)},
                navegarToRespirarRutinas={navController.navigate(Screens.RUTINAS)},
                navegarToRespirar={navController.navigate(Screens.RESPIRACION)},
                navegarToHistorial={navController.navigate(Screens.HISTORIAL)}
            )
        }
        composable(Screens.RUTINAS) {
            RutinaScreen()
        }
        composable(Screens.ESTADOANIMO) {
            EstadoScreen()

        }
        composable(Screens.RESPIRACION) {
            RespiraScreen()

        }
        composable(Screens.CONFIGURACION) {
            ConfiguracionScreen()

        }
        composable(Screens.HISTORIAL) {
            HistorialScreen()
        }
        composable(Screens.SPLASH) {
            SplashScreen(navController = navController)
        }
    }
    // Creamos un fichero llamado screens en donde se almacenaran las panrallas en forma de objetos
}