package com.example.findjob.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.findjob.R
import com.example.findjob.utils.CustomElevatedBtn
import com.example.findjob.utils.CustomText
import com.example.findjob.utils.CustomTxtField
import com.example.findjob.viewmodel.LoginUserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    viewModel: LoginUserViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6B6965)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFD9D0C7),
            ),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = "Giriş Ekranı", color = Color(0xFF1B1B1B), modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    fontSize = 40.sp
                )
                CustomTxtField(
                    value = email,
                    label = "Email Giriniz",
                    iconResId = R.drawable.baseline_email_24,
                    placeholder = "Email Giriniz",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(10.dp)
                )
                CustomTxtField(
                    value = password,
                    label = "Şifre Giriniz",
                    iconResId = R.drawable.baseline_lock_24,
                    placeholder = "Şifre Giriniz",
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                    modifier = Modifier.padding(10.dp)
                )
                CustomElevatedBtn(
                    text = "Giriş Yap",
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        viewModel.loginUser(
                            email = email.value,
                            password = password.value
                        )
                    })

                Text(
                    text = "Hesap Oluştur",
                    modifier = Modifier.clickable {
                        navController.navigate("register_screen")
                    },
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    LaunchedEffect(viewModel.message.value) {
        viewModel.message.value?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                viewModel.message.value = null
            }
        }
    }

    LaunchedEffect(viewModel.success.value) {
        viewModel.success.value?.let {
            coroutineScope.launch {
                if (it) {
                    navController.navigate("main_screen"){
                        popUpTo("login_screen") { inclusive = true }
                    }
                }
                viewModel.success.value = null
            }
        }
    }

}