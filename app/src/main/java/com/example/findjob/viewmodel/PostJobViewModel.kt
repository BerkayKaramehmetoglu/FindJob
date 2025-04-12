package com.example.findjob.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.findjob.datastore.getUserSession
import com.example.findjob.model.PostJob
import com.example.findjob.service.Services
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Base64
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.core.app.ActivityCompat
import java.io.ByteArrayOutputStream

class PostJobViewModel : ViewModel() {
    val message = mutableStateOf<String?>(null)

    private val _locationText = mutableStateOf("Konum")
    val locationText: State<String> = _locationText

    private val _latitude = mutableDoubleStateOf(0.0)
    private val latitude: State<Double> = _latitude

    private val _longitude = mutableDoubleStateOf(0.0)
    private val longitude: State<Double> = _longitude

    private var _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun removeImageUri() {
        _imageUri.value = null
    }

    private var _imageCamera = mutableStateOf<Bitmap?>(null)
    val imageCamera: State<Bitmap?> = _imageCamera

    fun setImageCamera(bitmap: Bitmap?) {
        _imageCamera.value = bitmap
    }

    fun removeCameraBitMap() {
        _imageCamera.value = null
    }

    fun encodeImageToBase64(context: Context, uri: Uri? = null, bitmap: Bitmap? = null): String? {
        return when {
            uri != null -> {
                val inputStream = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()
                byteArray?.let { Base64.encodeToString(it, Base64.DEFAULT) }
            }

            bitmap != null -> {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            }

            else -> null
        }
    }

    fun postJob(
        context: Context,
        jobTitle: String,
        jobDesc: String,
        jobPrice: String,
        image: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uid = getUserSession(context)
                val response = Services.userService.postJob(
                    PostJob(
                        uid!!,
                        jobTitle,
                        jobDesc,
                        jobPrice,
                        image,
                        latitude.value,
                        longitude.value
                    )
                )
                if (response.isSuccessful) {
                    message.value = response.body()?.message
                } else {
                    message.value = response.body()?.message
                }
            } catch (e: Exception) {
                println("Bağlantı hatası: ${e.localizedMessage}")
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun requestCurrentLocation(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.Builder(10000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        _latitude.doubleValue = location.latitude
                        _longitude.doubleValue = location.longitude
                        _locationText.value = "Konum Alındı"
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}