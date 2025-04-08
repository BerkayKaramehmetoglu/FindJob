package com.example.findjob.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.findjob.R
import com.example.findjob.model.GetJobs
import com.example.findjob.utils.DialogWithImage
import com.example.findjob.viewmodel.DeleteJobViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyJob(
    navController: NavController,
    jobList: List<GetJobs>,
    viewModel: DeleteJobViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0E5459),
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        text = "İş İlanlarım",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("main_screen") }) {
                        Icon(
                            modifier = Modifier.fillMaxSize(0.8f),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(0.08f),
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFFB34086))
        ) {
            items(jobList) { job ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2B9DA6),
                        contentColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            contentDescription = null,
                            tint = Color.Red,
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = job.currentTime,
                                textAlign = TextAlign.Center
                            )
                            AsyncImage(
                                model = job.downloadUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "Görüntülenme: 0",
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(bottom = 5.dp),
                                text = job.jobTitle.uppercase(),
                                textAlign = TextAlign.Center
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = job.jobDesc,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = job.jobAdrs,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 10.dp),
                                text = job.jobPrice,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Icon(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .clickable {
                                        viewModel.deleteJob(job.id)
                                    },
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = null,
                                tint = Color.Red
                            )
                            Icon(
                                modifier = Modifier.clickable {
                                    showDialog = true
                                },
                                imageVector = Icons.Rounded.Create,
                                contentDescription = null,
                                tint = Color.Yellow
                            )
                            if (showDialog) {
                                DialogWithImage(
                                    onDismissRequest = {
                                        showDialog = false
                                    },
                                    onConfirmation = {
                                        println("Onaylama İşlemi")
                                        showDialog = false
                                    },
                                    model = job.downloadUrl,
                                    jobs = job
                                )
                            }
                        }

                    }
                }
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
}