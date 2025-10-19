package com.example.examplemvvm.ui.screens.registro

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.Logo
import com.example.examplemvvm.ui.screens.componentes.TextFieldCreated1
import com.example.examplemvvm.ui.screens.componentes.TextFields
import androidx.compose.ui.Alignment

@Preview(showSystemUi = true)
@Composable
fun RegistroScreen(viewModel: RegistroViewModel = RegistroViewModel()) {
    Container() {
        Registro(modifier = Modifier, viewModel = viewModel)
    }

}

@Composable
fun Registro(modifier: Modifier, viewModel: RegistroViewModel) {
    val state by viewModel.state.observeAsState(RegistroState())
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imagen = painterResource(id = R.drawable.respira)
        )
        TextFieldCreated1(
            state.nombreUsuario,
            "Nombre de usuario",
            "Ejemplo:pepe@gmail.com"
        )
        { viewModel.onEvent(RegistroEvent.NombreUsuarioChanged(it)) }

        TextFieldCreated1(
            state.correo,
            "Correo",
            "Ingresa tu correo"
        ) { viewModel.onEvent(RegistroEvent.correoChanged(it)) }

        TextFieldCreated1(state.contrasena, "Contrasena", "Ingresa tu contrasena") {
            viewModel.onEvent(
                RegistroEvent.contrasenaChanged(it)
            )
        }

        TextFieldCreated1(
            state.confirmarContrasena,
            "Confirma contrasena",
            "Confirma tu contrasena",
        ) { viewModel.onEvent(RegistroEvent.confirmarContrasenaChanged(it)) }
        Spacer(modifier = Modifier.padding(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Label1(modifier = modifier)

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
                .background(color = Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButtonExample()
            FloatingActionButtonExample()
            FloatingActionButtonExample()
            FloatingActionButtonExample()
            FloatingActionButtonExample()
        }


    }

}

@Composable
fun Label1(modifier: Modifier) {
    Text(
        text = "Como te sintes el dia de hoy",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF41837B),

        )
}

@Composable
fun FloatingActionButtonExample() {
    FloatingActionButton(onClick = { print("Hello") }) {
        Icon(Icons.Filled.Edit, "Floating action button.")
    }
}

