package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface GetRoomByIdUseCase {
    suspend fun invoke(roomId: Int): Flow<Room>
}