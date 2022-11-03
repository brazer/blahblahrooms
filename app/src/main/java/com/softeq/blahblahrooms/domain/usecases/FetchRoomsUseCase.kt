package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface FetchRoomsUseCase {
    suspend fun fetch(): Flow<List<Room>>
}