package com.example.findjob.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.findjob.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJob(navController: NavController) {

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
                        text = "İş İlanı Yayınla",
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
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFFE642)),
            verticalArrangement = Arrangement.Center
        ) {
            val jobTitle = remember { mutableStateOf("") }
            val jobDesc = remember { mutableStateOf("") }
            val jobPrice = remember { mutableStateOf("") }

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_camera_alt_24),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.05f)
                            .clickable { /* Kamera İzinleri Alınacak */ }
                    )

                    VerticalDivider(color = Color.Black, modifier = Modifier.fillMaxHeight(0.1f))

                    Icon(
                        painter = painterResource(R.drawable.baseline_insert_photo_24),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.05f)
                            .clickable { /* Galeri İzinleri Alınacak*/ }
                    )
                }
            }

            CustomTxtField(
                value = jobTitle,
                label = "İş Başlığı",
                iconResId = R.drawable.baseline_title_24,
                placeholder = "İlan Başlığı",
                keyboardType = KeyboardType.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            CustomTxtField(
                value = jobDesc,
                label = "İş Açıklaması",
                iconResId = R.drawable.baseline_description_24,
                placeholder = "İş Açıklaması",
                keyboardType = KeyboardType.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            CustomTxtField(
                value = jobPrice,
                label = "İş Fiyatı",
                iconResId = R.drawable.baseline_attach_money_24,
                placeholder = "İş Fiyatı",
                keyboardType = KeyboardType.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "Konum Bilgisi Yazacak", color = Color.Black,
                        modifier = Modifier
                            .padding(20.dp),
                        fontSize = 20.sp,
                    )

                    VerticalDivider(color = Color.Black, modifier = Modifier.fillMaxHeight(0.22f))

                    FilledTonalButton(
                        modifier = Modifier.padding(20.dp),
                        onClick = { /*konum bilgisi alınacak*/ },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFFB34086)
                        )
                    ) {
                        Text(
                            text = "Konum Al",
                            color = Color(0xFFFFE642),
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            CustomElevatedBtn(
                "İlan Oluştur",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onClick = {
                    // ilan oluşturacak kod yazılacak
                    //ilan uygulamaya giren uid ile firebase e post edilecek ve öyle kaydedilecek
                }
            )

        }
    }
}

