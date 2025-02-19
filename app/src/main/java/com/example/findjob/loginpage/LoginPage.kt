package com.example.findjob.loginpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findjob.ui.theme.FindJobTheme
import com.example.findjob.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    snackbarHostState: SnackbarHostState
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E5459)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE642),
            ),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText("Giriş Ekranı", Color(0xFFB34086), fontSize = 40.sp)
                CustomTxtField(
                    value = email,
                    label = "Email Giriniz",
                    imageVector = Icons.Default.Email,
                    placeholder = "Email Giriniz",
                    keyboardType = KeyboardType.Email
                )
                CustomTxtField(
                    value = password,
                    label = "Şifre Giriniz",
                    imageVector = Icons.Default.Lock,
                    placeholder = "Şifre Giriniz",
                    isPassword = true,
                    keyboardType = KeyboardType.Password
                )
                CustomElevatedBtn(onClick = {
                    viewModel.loginUser(email = email.value, password = password.value)
                })

                Text(
                    "Hesap Oluştur",
                    modifier = Modifier.clickable {
                        /*TODO()*/ //hesap oluştur sayfasına aktaracak
                    },
                    fontSize = 17.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    LaunchedEffect(viewModel.loginMessage.value) {
        viewModel.loginMessage.value?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                viewModel.loginMessage.value = null
            }
        }
    }
}

@Composable
fun CustomText(
    text: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 20.sp
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize
    )
}

@Composable
fun CustomTxtField(
    value: MutableState<String>,
    label: String,
    imageVector: ImageVector,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val visualTransformation: VisualTransformation = if (isPassword) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
    OutlinedTextField(
        value = value.value,
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = null) },
        onValueChange = { value.value = it },
        label = { Text(text = label, fontSize = 20.sp) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.padding(10.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun CustomElevatedBtn(onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() }, colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFB34086)
        ),
        contentPadding = PaddingValues(50.dp, 20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            "Giriş Yap",
            color = Color(0xFFFFE642),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FindJobTheme {
        //Greeting()
    }
}