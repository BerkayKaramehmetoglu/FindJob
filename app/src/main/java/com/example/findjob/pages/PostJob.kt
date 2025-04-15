package com.example.findjob.pages

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.findjob.R
import com.example.findjob.utils.CustomElevatedBtn
import com.example.findjob.utils.CustomText
import com.example.findjob.utils.CustomTxtField
import com.example.findjob.utils.ObserveMessageAndRefresh
import com.example.findjob.viewmodel.GetJobsViewModel
import com.example.findjob.viewmodel.PostJobViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJob(
    navController: NavController,
    viewModelPostJob: PostJobViewModel,
    viewModelGetJobs: GetJobsViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val locationText by viewModelPostJob.locationText

    val imageUri by viewModelPostJob.imageUri
    val imageCamera by viewModelPostJob.imageCamera
    var image by remember { mutableStateOf<String?>("") }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModelPostJob.setImageUri(uri)
            image = viewModelPostJob.encodeImageToBase64(context, uri, null)
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                viewModelPostJob.setImageCamera(bitmap)
                image = viewModelPostJob.encodeImageToBase64(context, null, bitmap)
            }
        }

    val permissionGranted = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coroutineScope = rememberCoroutineScope()

    val locationPermissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModelPostJob.requestCurrentLocation(context)
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    "Konum izni verilmedi",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFD9D0C7),
                ),
                title = {
                    Text(
                        text = "İş İlanı Yayınla",
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
                .background(Color(0xFF6B6965)),
            verticalArrangement = Arrangement.Center
        ) {
            val jobTitle = remember { mutableStateOf("") }
            val jobDesc = remember { mutableStateOf("") }
            val jobPrice = remember { mutableStateOf("") }

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (imageUri == null && imageCamera == null) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_camera_alt_24),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    cameraLauncher.launch(null)
                                }
                                .size(45.dp)
                        )
                        VerticalDivider(
                            color = Color.Black, modifier = Modifier
                                .height(45.dp)
                                .width(1.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_insert_photo_24),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    pickImageLauncher.launch("image/*")
                                }
                                .size(45.dp)
                        )
                    } else {
                        imageUri?.let {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        viewModelPostJob.removeImageUri()
                                    },
                                contentScale = ContentScale.Crop,
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = null
                            )
                        }

                        imageCamera?.let {
                            Image(
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        viewModelPostJob.removeCameraBitMap()
                                    },
                                contentScale = ContentScale.Crop,
                                bitmap = it.asImageBitmap(),
                                contentDescription = null
                            )
                        }
                    }
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
                label = "İş Fiyatı ₺",
                iconResId = R.drawable.baseline_attach_money_24,
                placeholder = "İş Fiyatı ₺",
                keyboardType = KeyboardType.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomText(
                        text = locationText,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )

                    VerticalDivider(
                        color = Color.Black, modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                    )

                    FilledTonalButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(15.dp),
                        onClick = {
                            if (permissionGranted) {
                                viewModelPostJob.requestCurrentLocation(context)
                            } else {
                                locationPermissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                locationPermissionRequestLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            }
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF1B1B1B)
                        )
                    ) {
                        Text(
                            text = "Konum Al",
                            color = Color.White,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            CustomElevatedBtn(
                "İlanımı Oluştur",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onClick = {
                    viewModelPostJob.postJob(
                        context = context,
                        jobTitle = jobTitle.value,
                        jobDesc = jobDesc.value,
                        jobPrice = jobPrice.value,
                        image = image!!
                    )
                }
            )
        }
    }

    ObserveMessageAndRefresh(
        message = viewModelPostJob.message.value,
        clearMessage = { viewModelPostJob.message.value = null },
        onSuccess = {
            viewModelGetJobs.getJobs(user = true, context = context)
        },
        snackbarHostState = snackbarHostState
    )
}


