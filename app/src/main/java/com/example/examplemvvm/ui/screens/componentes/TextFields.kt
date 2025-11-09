package com.example.examplemvvm.ui.screens.componentes

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.String


@SuppressLint("RememberInComposition")
@Composable
fun TxtFieldGeneral(valor: String, etiqueta:String, placeholderTexto:String, modifier: Modifier= Modifier,errorMessage: String = "", onTextFieldChanged: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = valor,
            onValueChange = { onTextFieldChanged(it) },
            modifier = Modifier.fillMaxWidth(),
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
fun TxtPasswordField(
    valor: String,
    etiqueta: String,
    placeholderTexto: String,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onTextFieldChanged: (String) -> Unit
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = valor,
            onValueChange = { onTextFieldChanged(it) },
            modifier = modifier
                .fillMaxWidth(),
            label = { Text(text = etiqueta) },
            placeholder = { Text(text = placeholderTexto) },
            singleLine = true,
            maxLines = 1,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF367A72),
                unfocusedTextColor = Color(0xFF132F2C),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp)
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
