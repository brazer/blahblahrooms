package com.softeq.blahblahrooms.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.data.models.PlacementDTO
import com.softeq.blahblahrooms.domain.models.Room

fun PlacementDTO.asRoom(): Room {
    return Room(
        id = this.id,
        userId = this.userId,
        price = this.price.toFloat(),
        location = LatLng(this.latitude, this.longitude),
        address = this.address,
        period = if (this.period == 0) Period.SHORT else Period.LONG,
        description = this.description,
        email = this.contacts,
        city = this.city
    )
}

fun Room.asPlacementDTO(): PlacementDTO {
    return PlacementDTO(
        id = this.id,
        type = 0,
        description = this.description,
        contacts = this.email,
        latitude = this.location.latitude,
        longitude = this.location.longitude,
        address = this.address,
        city = this.city,
        price = this.price.toDouble(),
        active = true,
        period = if (this.period == Period.SHORT) 0 else 1,
        userId = this.userId
    )
}