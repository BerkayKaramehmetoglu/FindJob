package com.example.findjob

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findjob.datastore.getUserSession
import com.example.findjob.pages.FavoriteJobs
import com.example.findjob.pages.LoginPage
import com.example.findjob.pages.MainPage
import com.example.findjob.pages.MyJob
import com.example.findjob.pages.PostJob
import com.example.findjob.pages.RegisterPage
import com.example.findjob.ui.theme.FindJobTheme
import com.example.findjob.viewmodel.DeleteJobViewModel
import com.example.findjob.viewmodel.GetJobsViewModel
import com.example.findjob.viewmodel.LoginUserViewModel
import com.example.findjob.viewmodel.PostJobViewModel
import com.example.findjob.viewmodel.RegisterUserViewModel
import com.example.findjob.viewmodel.UpdateJobViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModelRegister: RegisterUserViewModel by viewModels()
    private val viewModelLogin: LoginUserViewModel by viewModels()
    private val viewModelPostJob: PostJobViewModel by viewModels()
    private val viewModelGetJobs: GetJobsViewModel by viewModels()
    private val viewModelDeleteJob: DeleteJobViewModel by viewModels()
    private val viewModelUpdateJob: UpdateJobViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindJobTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf("login_screen") }
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        val uid = getUserSession(applicationContext)
                        startDestination = if (uid != null) "main_screen" else "login_screen"
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0.dp),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
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
                                viewModelGetJobs.getJobs(context = context)
                                MainPage(
                                    navController = navController,
                                    jobList = viewModelGetJobs.allJobsList.value
                                )
                            }

                            composable("postjob_screen") {
                                PostJob(
                                    navController = navController,
                                    viewModelPostJob = viewModelPostJob,
                                    viewModelGetJobs = viewModelGetJobs,
                                    snackbarHostState = snackbarHostState
                                )
                            }

                            composable("myjob_screen") {
                                viewModelGetJobs.getJobs(user = true, context = context)
                                MyJob(
                                    navController = navController,
                                    jobList = viewModelGetJobs.myJobsList.value,
                                    viewModelDelete = viewModelDeleteJob,
                                    viewModelUpdate = viewModelUpdateJob,
                                    viewModelGetJobs = viewModelGetJobs,
                                    snackbarHostState = snackbarHostState
                                )
                            }

                            composable("favjob_screen") {
                                viewModelGetJobs.getJobs(fav = true, context = context)
                                FavoriteJobs(
                                    navController = navController,
                                    jobList = viewModelGetJobs.favJobsList.value,
                                    viewModelGetJobs = viewModelGetJobs
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}