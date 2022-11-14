package com.softeq.blahblahrooms.data.providers

import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

import java.util.*

object CurrentLocationProvider {

    @Stable
    var location: LatLng? = null

    @Suppress("deprecation")
    fun getAddress(context: Context, callback: (String?) -> Unit) {
        location?.apply {
            val geoCoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geoCoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    callback(addresses.firstOrNull()?.getAddressLine(0))
                }
            } else {
                val list = geoCoder.getFromLocation(latitude, longitude, 1)
                callback(list?.firstOrNull()?.getAddressLine(0))
            }
        } ?: callback(null)
    }

    @Suppress("deprecation")
    fun getCity(context: Context, callback: (String?) -> Unit) {
        location?.apply {
            val geoCoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geoCoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    callback(addresses.firstOrNull()?.locality)
                }
            } else {
                val list = geoCoder.getFromLocation(latitude, longitude, 1)
                callback(list?.firstOrNull()?.locality)
            }
        } ?: callback(null)
    }
}