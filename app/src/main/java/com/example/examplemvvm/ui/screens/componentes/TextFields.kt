package com.example.examplemvvm.ui.screens.componentes

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextFields(valor: String, etiqueta:String, placeholderTexto:String, modifier: Modifier= Modifier,errorMessage: String = "", onTextFieldChanged: (String) -> Unit){
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = valor,
            onValueChange = { onTextFieldChanged(it) },
            modifier = modifier.fillMaxWidth().padding(bottom = 5.dp),
            label = { Text(etiqueta) },
            placeholder = { Text(placeholderTexto) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF367A72),
                unfocusedTextColor = Color(0xFF132F2C),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}

@SuppressLint("RememberInComposition")
@Composable
fun TextFieldCreated1(valor: String, etiqueta:String, placeholderTexto:String, modifier: Modifier= Modifier, onTextFieldChanged: (String) -> Unit) {
        OutlinedTextField(
            value = valor,
            onValueChange = { onTextFieldChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            textStyle = TextStyle(color = Color(0xFF367A72)),
            label = { Text(text = etiqueta) },
            placeholder = { Text(text = placeholderTexto) },
            /*leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "")
            },*/
            isError = false,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
            minLines = 1,
            interactionSource = MutableInteractionSource(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF367A72),
                unfocusedTextColor = Color(0xFF132F2C),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )
}