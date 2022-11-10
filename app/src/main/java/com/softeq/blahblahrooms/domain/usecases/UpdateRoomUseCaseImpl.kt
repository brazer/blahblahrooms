package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateRoomUseCaseImpl(
    private val repo: RoomsRepo,
    private val localRepo: RoomsLocalRepo
) : UpdateRoomUseCase {
    override suspend fun invoke(room: Room) = withContext(Dispatchers.IO) {
        val room = repo.updateRoom(room)
        localRepo.updateRoom(room)
    }
}