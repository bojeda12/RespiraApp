package com.example.examplemvvm.ui.screens.estado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container

@Preview
@Composable
fun EstadoScreen(){
    Estados()
}


@Composable
fun Estados(){
    Container(
        showEncabezado = true,
        showBackButton = true,
        onBackClick = {}
        ) {
        Column(modifier = Modifier
            .padding(top = 50.dp)
            .height(550.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LblEstado()
            BotonEstados(estado = "Muy bien", dibuja = painterResource(id = R.drawable.happyface))
            BotonEstados(estado = "Bien", dibuja = painterResource(id = R.drawable.happy))
            BotonEstados(estado = "Neutro", dibuja = painterResource(id = R.drawable.confused))
            BotonEstados(estado = "Mal", dibuja = painterResource(id = R.drawable.sad))
            BotonEstados(estado = "Muy mal", dibuja = painterResource(id = R.drawable.sadface))
        }

    }
}

@Composable
fun LblEstado() {
    Text(
        "Como te sientes el dia de hoy",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 30.sp
    )
}
@Composable
fun BotonEstados(
    modifier: Modifier = Modifier,
    estado: String = "",
    dibuja: Painter
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(35))
            .background(Color(0xFF359B94))
            .clickable{}

    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                //painterResource(id = R.drawable.respira)
                modifier = Modifier
                    .size(34.dp),
                painter = dibuja,
                contentDescription = ""
            )
        }
        Box(
            modifier
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text(
                estado,
                color = Color.White,
                fontSize = 18.sp,
                modifier = modifier.width(180.dp),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

