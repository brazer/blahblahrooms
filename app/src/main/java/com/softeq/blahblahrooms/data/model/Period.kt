package com.softeq.blahblahrooms.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Period {

    @SerialName("Long")
    LONG,

    @SerialName("Short")
    SHORT
}
