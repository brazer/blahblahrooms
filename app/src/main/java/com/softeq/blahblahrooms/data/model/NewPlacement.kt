package com.softeq.blahblahrooms.data.model

import com.softeq.blahblahrooms.data.DataConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPlacement(
    @SerialName("type")
    val type: String = DataConfig.PLACEMENT_TYPE_FLAT,
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
    val period: String = DataConfig.PERIOD_TYPE_SHORT,
    @SerialName("userId")
    val userId: String = ""
)