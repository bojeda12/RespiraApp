package com.example.examplemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.examplemvvm.navigation.NavigationWrapper
import com.example.examplemvvm.ui.theme.ExampleMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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



