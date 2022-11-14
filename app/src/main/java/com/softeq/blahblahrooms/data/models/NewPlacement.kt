package com.softeq.blahblahrooms.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPlacement(
    @SerialName("type")
    val type: PlacementType = PlacementType.FLAT,
    @SerialName("description")
    val description: String = "",
    @SerialName("contacts")
    val contacts: String = "",
    @SerialName("longitude")
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
    val period: Period = Period.SHORT,
    @SerialName("userId")
    val userId: String = ""
)