package com.example.examplemvvm.ui.screens.componenetes

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.examplemvvm.R

@Composable
fun Logo(modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.respira),
        contentDescription = "",
        modifier = Modifier
    )
}