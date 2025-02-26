package com.example.findjob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findjob.datastore.getUserSession
import com.example.findjob.pages.LoginPage
import com.example.findjob.pages.MainPage
import com.example.findjob.pages.RegisterPage
import com.example.findjob.ui.theme.FindJobTheme
import com.example.findjob.viewmodel.LoginUserViewModel
import com.example.findjob.viewmodel.RegisterUserViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModelRegister: RegisterUserViewModel by viewModels()
    private val viewModelLogin: LoginUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FindJobTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf("login_screen") }

                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        val uid = getUserSession(applicationContext)
                        startDestination = if (uid != null) "main_screen" else "login_screen"
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable("login_screen") {
                                LoginPage(
                                    viewModel = viewModelLogin,
                                    snackbarHostState = snackbarHostState,
                                    navController = navController
                                )
                            }

                            composable("register_screen") {
                                RegisterPage(
                                    viewModel = viewModelRegister,
                                    snackbarHostState = snackbarHostState,
                                    navController = navController
                                )
                            }

                            composable("main_screen") {
                                MainPage(navController) { paddingValues ->
                                    Box(modifier = Modifier.padding(paddingValues)) {
                                        Text(text = "Merhaba, dünya!")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
