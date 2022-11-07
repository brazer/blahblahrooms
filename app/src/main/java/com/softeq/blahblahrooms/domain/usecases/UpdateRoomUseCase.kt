package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room

interface UpdateRoomUseCase {
    suspend fun invoke(room: Room)
}