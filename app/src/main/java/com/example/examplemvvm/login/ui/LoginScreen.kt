package com.example.examplemvvm.login.ui
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.R
/*
* Creamos el vie model en el login screen y podemos pasar esos parametros en donde los vayamos a utilziar
* */


@Composable
fun LoginScreen(viewModel: LoginViewModel){//Definimos el view model en la raiz de todos los componentes ya que de aqui parte o heredan los demas esta caracateristica del view model
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
            Login(Modifier.align(Alignment.Center),viewModel)
        }

    }

}

@Composable
fun Login(modifier: Modifier,viewModel: LoginViewModel) {

    val email : String by viewModel.email.observeAsState(initial = "")//Cremaos el evento que observa los cambios con el observer state
    val password : String by viewModel.password.observeAsState(initial = "")
    val loginEnable : Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
     {
        HeaderImage(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier= Modifier.padding(10.dp))
        EmailField(email,{viewModel.onLoginChange(it, password)})
        Spacer(modifier= Modifier.padding(18.dp))
        PasswordField(password, {viewModel.onLoginChange(email,it)})
        Spacer(modifier= Modifier.padding(4.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier= Modifier.padding(10.dp))
        LoginButton(loginEnable){viewModel.onLoginSelected()}
        Spacer(modifier= Modifier.padding(4.dp))
        Registrate()
    }
}

@Composable
fun Registrate() {
    Text(
        text = "No tienes una cuenta, registrte ahora",
        modifier = Modifier.clickable{},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF41837B))
}


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {onLoginSelected},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors
            (
                containerColor = Color(0xFF359B94),
                disabledContainerColor = Color(0xFF347771),
                contentColor = Color(0xFFFFFFFF),
                disabledContentColor = Color(0xFFFFFFFF)
            ),
        enabled = loginEnable
    )
    {
        Text(text = "Iniciar sesion")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contrasena",
        modifier = modifier.clickable{},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF41837B))
}

@Composable
fun PasswordField(password: String, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF41837B),
            unfocusedTextColor = Color(0xFF57C4BC),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged:(String) -> Unit) {

    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF367A72),
            unfocusedTextColor = Color(0xFF132F2C),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.respira),
        contentDescription = "",
        modifier = Modifier
    )
}