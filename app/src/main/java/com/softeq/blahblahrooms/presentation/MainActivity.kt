package com.softeq.blahblahrooms.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.providers.CurrentLocationProvider
import com.softeq.blahblahrooms.presentation.route.Navigation
import com.softeq.blahblahrooms.presentation.ui.BlahBlahRoomsTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    private val permissionFlow = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (!permissions.containsValue(false)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Timber.d("Location = $location")
                if (location != null) {
                    CurrentLocationProvider.location = LatLng(location.latitude, location.longitude)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionFlow.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
        setContent {
            BlahBlahRoomsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }

}