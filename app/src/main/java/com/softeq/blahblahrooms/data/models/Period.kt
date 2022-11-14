package com.softeq.blahblahrooms.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Period {

    @SerialName("Long")
    LONG,

    @SerialName("Short")
    SHORT
}
