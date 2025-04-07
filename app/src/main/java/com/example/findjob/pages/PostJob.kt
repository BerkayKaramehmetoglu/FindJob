package com.example.findjob.pages

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.findjob.R
import com.example.findjob.utils.CustomElevatedBtn
import com.example.findjob.utils.CustomText
import com.example.findjob.utils.CustomTxtField
import com.example.findjob.viewmodel.PostJobViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJob(
    navController: NavController,
    viewModel: PostJobViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val locationText by viewModel.locationText
    val coroutineScope = rememberCoroutineScope()

    val imageUri by viewModel.imageUri
    val imageCamera by viewModel.imageCamera
    var show by remember { mutableStateOf(true) }
    var image by remember { mutableStateOf<String?>("") }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setImageUri(uri)
            image = viewModel.encodeImageToBase64(context, uri, null)
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                viewModel.setImageCamera(bitmap)
                image = viewModel.encodeImageToBase64(context, null, bitmap)
            }
        }

    LaunchedEffect(Unit) {
        viewModel.initializeLocationClient(context)
    }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (show && (imageUri == null || imageCamera == null)) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_camera_alt_24),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    cameraLauncher.launch(null)
                                    show = false
                                }
                        )

                        VerticalDivider(
                            color = Color.Black, modifier = Modifier
                                .height(40.dp)
                                .width(1.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_insert_photo_24),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    pickImageLauncher.launch("image/*");
                                    show = false
                                }
                        )
                    } else {
                        imageUri?.let {
                            Image(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        show = true
                                        viewModel.setImageUri(null)
                                    },
                                contentScale = ContentScale.Crop,
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = null
                            )
                        }

                        imageCamera?.let {
                            Image(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        show = true
                                        viewModel.setImageCamera(null)
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomText(
                        text = locationText,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp),
                    )

                    VerticalDivider(
                        color = Color.Black, modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                    )

                    FilledTonalButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp),
                        onClick = {
                            viewModel.requestCurrentLocation(context)
                        },
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
                    viewModel.postJob(
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


