package com.softeq.blahblahrooms.domain.repositories

import com.softeq.blahblahrooms.domain.models.Room

interface RoomsRepo {
    suspend fun getRooms(): List<Room>
    suspend fun addRoom(room: Room): Room
    suspend fun updateRoom(room: Room): Room
    suspend fun deleteRoom(room: Room)
}