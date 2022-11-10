package com.softeq.blahblahrooms.data.mappers

import com.softeq.blahblahrooms.data.model.NewPlacement
import com.softeq.blahblahrooms.data.model.PlacementType
import com.softeq.blahblahrooms.domain.models.Room


fun Room.asNewPlacement(): NewPlacement {
    return NewPlacement(
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