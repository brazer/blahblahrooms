package com.softeq.blahblahrooms.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.model.PlacementAPI
import com.softeq.blahblahrooms.data.model.PlacementType
import com.softeq.blahblahrooms.domain.models.Room

fun Room.asPlacementAPI(): PlacementAPI {
    return PlacementAPI(
        id = this.id,
        type = PlacementType.FLAT,
        description = this.description,
        contacts = this.email,
        longitude = this.location.longitude,
        latitude = this.location.latitude,
        address = this.address,
        city = "",
        price = this.price.toDouble(),
        active = true,
        period = this.period,
        userId = this.userId
    )
}

fun PlacementAPI.asRoom(): Room {
    return Room(
        id = this.id,
        userId = this.userId,
        price = this.price.toFloat(),
        location = LatLng(this.latitude, this.longitude),
        address = this.address,
        period = this.period,
        description = this.description,
        email = this.contacts
    )
}