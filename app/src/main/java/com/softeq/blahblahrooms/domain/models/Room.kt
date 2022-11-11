package com.softeq.blahblahrooms.domain.models

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.models.Period

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