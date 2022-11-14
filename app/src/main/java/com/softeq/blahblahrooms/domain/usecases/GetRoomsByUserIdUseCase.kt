package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface GetRoomsByUserIdUseCase {
    suspend fun invoke(): Flow<List<Room>>
}