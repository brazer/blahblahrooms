package com.softeq.blahblahrooms.data.mappers

import com.softeq.blahblahrooms.data.models.RoomMarker
import com.softeq.blahblahrooms.domain.models.Room

fun Collection<Room>.map(): List<RoomMarker> {
    return map { RoomMarker(it) }
}