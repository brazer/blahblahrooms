package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room

interface AddRoomUseCase {
    suspend fun invoke(room: Room)
}