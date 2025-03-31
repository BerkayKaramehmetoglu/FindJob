package com.example.findjob.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
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
import java.util.Locale
import android.util.Base64
import java.io.ByteArrayOutputStream

class PostJobViewModel : ViewModel() {
    val message = mutableStateOf<String?>(null)

    private val _locationText = mutableStateOf("Konum")
    val locationText: State<String> = _locationText

    private var _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    private var _imageCamera = mutableStateOf<Bitmap?>(null)
    val imageCamera: State<Bitmap?> = _imageCamera

    fun setImageCamera(bitmap: Bitmap?){
        _imageCamera.value = bitmap
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun postJob(
        context: Context,
        jobTitle: String,
        jobDesc: String,
        jobPrice: String,
        image: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val location = if (_locationText.value == "Konum") null else _locationText.value
                val uid = getUserSession(context)
                val response = Services.userService.postJob(
                    PostJob(
                        uid!!,
                        jobTitle,
                        jobDesc,
                        jobPrice,
                        location,
                        image
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

    fun initializeLocationClient(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    fun requestCurrentLocation(context: Context) {
        val locationRequest = LocationRequest.Builder(10000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        getCityName(context, location.latitude, location.longitude) { cityName ->
                            _locationText.value = cityName
                        }
                        return
                    }
                }
                _locationText.value = "Konum alınamadı"
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getCityName(
        context: Context,
        latitude: Double,
        longitude: Double,
        onResult: (String) -> Unit
    ) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())

            geocoder.getFromLocation(
                latitude, longitude, 1
            ) { addresses ->
                if (addresses.isNotEmpty()) {
                    val firstAddress = addresses.first()
                    val countryName = firstAddress.countryName ?: "Ülke bilgisi yok"
                    val cityName =
                        firstAddress.locality ?: firstAddress.adminArea ?: "Şehir bilgisi yok"
                    val streetName = firstAddress.thoroughfare ?: "Sokak bilgisi yok"

                    onResult("$cityName/$countryName\n$streetName")
                } else {
                    onResult("Adres bilgisi alınamadı")
                }
            }
        } catch (e: Exception) {
            onResult("Hata: ${e.localizedMessage}")
        }
    }
}