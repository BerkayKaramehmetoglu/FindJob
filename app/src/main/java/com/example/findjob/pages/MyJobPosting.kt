package com.example.findjob.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.findjob.R
import com.example.findjob.model.GetJobs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyJob(navController: NavController, jobList: List<GetJobs>) {
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
                    Row(modifier = Modifier.padding(10.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            contentDescription = null,
                            tint = Color.Red,
                        )
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 30.dp),
                                text = "1/04/2025"
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = "Görüntülenme: 0"
                            )
                        }
                        Spacer(modifier = Modifier.padding(start = 50.dp))

                        Column {
                            Text(job.jobTitle.uppercase())
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(modifier = Modifier.padding(top = 60.dp), text = job.jobDesc)
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(modifier = Modifier.padding(top = 60.dp), text = job.jobAdrs)
                            Text(modifier = Modifier.padding(start = 30.dp), text = job.jobPrice)
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                        Column {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = null,
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Icon(
                                Icons.Rounded.Create,
                                contentDescription = null,
                                tint = Color.Yellow
                            )
                        }

                    }
                }
            }
        }
    }
}