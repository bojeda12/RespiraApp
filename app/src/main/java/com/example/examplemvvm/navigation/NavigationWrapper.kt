package com.example.examplemvvm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginScreen
import com.example.examplemvvm.ui.theme.login.ui.screens.registro.RegistroScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login){
        composable<Login> { LoginScreen() }
        composable<Registro> { }
    }
   // Creamos un fichero llamado screens en donde se almacenaran las panrallas en forma de objetos


}