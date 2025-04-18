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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.findjob.datastore.removeFavJob
import com.example.findjob.model.GetJobs
import com.example.findjob.viewmodel.GetJobsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteJobs(
    navController: NavController,
    jobList: List<GetJobs>,
    viewModelGetJobs: GetJobsViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFD9D0C7),
                ),
                title = {
                    Text(
                        text = "Favori İş İlanlarım",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
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
                        )
                    }
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(0.08f),
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                    )
                }
            )
        })
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF6B6965))
        ) {
            items(jobList) { job ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF9E8B6A),
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
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = job.currentTime,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            AsyncImage(
                                model = job.downloadUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(250.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .align(Alignment.CenterHorizontally)
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
                                text = "Konum: ${job.jobCity.uppercase()}",
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                text = "Fiyat: ${job.jobPrice} ₺",
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Icon(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            removeFavJob(context, job.id)
                                            viewModelGetJobs.getJobs(fav = true, context = context)
                                        }
                                    }
                                    .size(30.dp),
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}