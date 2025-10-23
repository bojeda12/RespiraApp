package com.example.examplemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.examplemvvm.navigation.NavigationWrapper
import com.example.examplemvvm.ui.theme.ExampleMVVMTheme
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginScreen
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleMVVMTheme {
                NavigationWrapper() //aqui se carga la navegacion que creamos en la carpeta de navegacion
                }
            }
        }
    }



