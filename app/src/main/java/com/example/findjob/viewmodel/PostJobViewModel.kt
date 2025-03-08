package com.example.findjob.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

class PostJobViewModel : ViewModel() {
    private val _locationText = mutableStateOf("Konum Alınıyor")
    val locationText: State<String> = _locationText

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
                _locationText.value = "Konum alınamadı (requestLocationUpdates)"
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

                    onResult("$cityName, $countryName ($streetName)")
                } else {
                    onResult("Adres bilgisi alınamadı")
                }
            }
        } catch (e: Exception) {
            onResult("Hata: ${e.localizedMessage}")
        }
    }
}