package com.softeq.blahblahrooms.data.mappers

import com.softeq.blahblahrooms.data.DataConfig
import com.softeq.blahblahrooms.data.model.NewPlacement
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room


fun Room.asNewPlacement(): NewPlacement {
    return NewPlacement(
        type = DataConfig.PLACEMENT_TYPE_FLAT,
        description = this.description,
        contacts = this.email,
        longitude = this.location.longitude,
        latitude = this.location.latitude,
        address = this.address,
        city = "",
        price = this.price.toDouble(),
        active = true,
        period = if (this.period == Period.SHORT) DataConfig.PERIOD_TYPE_SHORT else DataConfig.PERIOD_TYPE_LONG,
        userId = this.userId
    )
}