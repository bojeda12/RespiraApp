package com.example.examplemvvm.ui.screens.componentes

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.examplemvvm.R

@Composable
fun Logo(modifier: Modifier,imagen: Painter){
    Image(
        //painterResource(id = R.drawable.respira)
        painter = imagen,
        contentDescription = "",
        modifier = modifier
    )
}