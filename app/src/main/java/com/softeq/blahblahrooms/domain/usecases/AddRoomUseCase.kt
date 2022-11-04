package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface AddRoomUseCase {
    suspend fun add(room: Room): Flow<Unit>
}