package com.softeq.blahblahrooms.domain.repositories

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface RoomsLocalRepo {
    fun getRooms(): Flow<List<Room>>
    fun getRoomsByUserId(userId: String): Flow<List<Room>>
    fun getRoomById(roomId: Int): Flow<Room>
    fun setRooms(rooms: List<Room>)
    fun setRoom(room: Room)
    fun updateRoom(room: Room)
    fun delete(room: Room)
}