package com.softeq.blahblahrooms.domain.repositories

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepo {
    suspend fun getRooms(): Flow<List<Room>>
}