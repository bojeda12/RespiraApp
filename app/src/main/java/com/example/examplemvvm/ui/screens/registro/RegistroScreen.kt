package com.example.examplemvvm.ui.screens.registro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun registroScreen(modifier: Modifier = Modifier){
    Box(Modifier
        .fillMaxSize()
        .padding(top = 100.dp)
        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        .background(MaterialTheme.colorScheme.background)

    )
    {

        Box(Modifier
            .fillMaxSize()
            .padding(horizontal = 45.dp)
        ){

        }

    }

}

