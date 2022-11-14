package com.softeq.blahblahrooms.data.mappers

import com.softeq.blahblahrooms.data.models.NewPlacement
import com.softeq.blahblahrooms.data.models.PlacementType
import com.softeq.blahblahrooms.domain.models.Room

fun Room.asNewPlacement(): NewPlacement {
    return NewPlacement(
        type = PlacementType.FLAT,
        description = this.description,
        contacts = this.email,
        longitude = this.location.longitude,
        latitude = this.location.latitude,
        address = this.address,
        city = this.city,
        price = this.price.toDouble(),
        active = true,
        period = this.period,
        userId = this.userId
    )
}