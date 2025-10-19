package com.example.examplemvvm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examplemvvm.ui.screens.registro.RegistroScreen
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginScreen


@Composable
fun NavigationWrapper(){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LOGIN){
        composable(Screens.LOGIN) { LoginScreen(navegarRegistro = {
            navController.navigate(Screens.REGISTRO)
        }) }
        composable(Screens.REGISTRO){
            RegistroScreen()
        }

    }
   // Creamos un fichero llamado screens en donde se almacenaran las panrallas en forma de objetos
}