package com.softeq.blahblahrooms.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlacementType {

    @SerialName("Flat")
    FLAT,

    @SerialName("Room")
    ROOM
}