package com.example.examplemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.examplemvvm.navigation.NavigationWrapper
import com.example.examplemvvm.navigation.Screens
import com.example.examplemvvm.ui.screens.splashScreen.SplashViewModel
import com.example.examplemvvm.ui.theme.ExampleMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)

        val destino = intent?.getStringExtra("destino")

        setContent {
            ExampleMVVMTheme {
                val haySesion by splashViewModel.haySesion.collectAsState()

                haySesion?.let { tieneSesion ->
                    val startDestination = when {
                        destino == "respira" && tieneSesion -> Screens.RESPIRACION
                        destino == "respira" && !tieneSesion -> Screens.LOGIN
                        tieneSesion -> Screens.DASHBOARD
                        else -> Screens.LOGIN
                    }
                    NavigationWrapper(startDestination = startDestination)
                }
            }
        }
    }
}








