package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo

class UpdateRoomUseCaseImpl(
    private val roomsRepo: RoomsRepo
) : UpdateRoomUseCase {
    override suspend fun invoke(room: Room) {
        roomsRepo.updateRoom(room)
    }
}