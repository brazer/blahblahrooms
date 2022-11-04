package com.softeq.blahblahrooms.domain.models

import com.google.android.gms.maps.model.LatLng

data class Room(
    val id: Int,
    val userId: String,
    val price: Float,
    val location: LatLng,
    val address: String,
    val period: Period,
    val description: String,
    val email: String
)

enum class Period {
    LONG, SHORT
}
