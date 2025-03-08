package com.example.findjob.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.Locale

/*@Composable
fun GetLastKnownLocation() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationText by remember { mutableStateOf("Konum alınıyor...") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requestCurrentLocation(context, fusedLocationClient) { location ->
                locationText = location ?: "Konum alınamadı"
            }
        } else {
            locationText = "Konum izni reddedildi"
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            requestCurrentLocation(context, fusedLocationClient) { location ->
                locationText = location ?: "Konum alınamadı"
            }
        } else {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}

@SuppressLint("MissingPermission")
fun requestCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (String?) -> Unit
) {
    val locationRequest = LocationRequest.Builder(10000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    val locationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    getCityName(context, location.latitude, location.longitude) { cityName ->
                        onLocationReceived(cityName)
                    }
                    return
                }
            }
            onLocationReceived("Konum alınamadı (requestLocationUpdates)")
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getCityName(context: Context, latitude: Double, longitude: Double, onResult: (String) -> Unit) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())

        geocoder.getFromLocation(latitude, longitude, 1
        ) { addresses ->
            if (addresses.isNotEmpty()) {
                val firstAddress = addresses.first()
                val countryName = firstAddress.countryName ?: "Ülke bilgisi yok"
                val cityName =
                    firstAddress.locality ?: firstAddress.adminArea ?: "Şehir bilgisi yok"
                val streetName = firstAddress.thoroughfare ?: "Sokak bilgisi yok"

                onResult("$cityName, $countryName ($streetName)")
            } else {
                onResult("Adres bilgisi alınamadı")
            }
        }
    } catch (e: Exception) {
        onResult("Hata: ${e.localizedMessage}")
    }
}*/