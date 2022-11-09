package com.softeq.blahblahrooms.data.model

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlacementAPI(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("type")
    val type: String = PlacementType.Flat.name,
    @SerialName("description")
    val description: String = "",
    @SerialName("contacts")
    val contacts: String = "",
    val longitude: Double = 0.0,
    @SerialName("latitude")
    val latitude: Double = 0.0,
    @SerialName("address")
    val address: String = "",
    @SerialName("city")
    val city: String = "",
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("active")
    val active: Boolean = true,
    @SerialName("period")
    val period: String = PeriodType.Short.name,
    @SerialName("userId")
    val userId: String = ""
)

fun Room.asPlacementAPI(): PlacementAPI {
    return PlacementAPI(
        id = this.id,
        type = PlacementType.Flat.name,
        description = this.description,
        contacts = this.email,
        longitude = this.location.longitude,
        latitude = this.location.latitude,
        address = this.address,
        city = "",
        price = this.price.toDouble(),
        active = true,
        period = if (this.period == Period.SHORT) PeriodType.Short.name else PeriodType.Long.name,
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
        period = if (this.period == PeriodType.Short.name) Period.SHORT else Period.LONG,
        description = this.description,
        email = this.contacts
    )
}