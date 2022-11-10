package com.softeq.blahblahrooms.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.model.PlacementDTO
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room

fun PlacementDTO.asRoom(): Room {
    return Room(
        id = this.id,
        userId = this.userId,
        price = this.price.toFloat(),
        location = LatLng(this.coordinates, 0.0),
        address = this.address,
        period = if (this.period == 0) Period.SHORT else Period.LONG,
        description = this.description,
        email = this.contacts
    )
}

fun Room.asPlacementDTO(): PlacementDTO {
    return PlacementDTO(
        id = this.id,
        type = 0,
        description = this.description,
        contacts = this.email,
        coordinates = this.location.latitude,
        address = this.address,
        city = "",
        price = this.price.toDouble(),
        active = true,
        period = if (this.period == Period.SHORT) 0 else 1,
        userId = this.userId
    )
}