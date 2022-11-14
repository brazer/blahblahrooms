package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddRoomUseCaseImpl @Inject constructor(
    private val repo: RoomsRepo,
    private val localRepo: RoomsLocalRepo
) : AddRoomUseCase {
    override suspend fun invoke(room: Room) = withContext(Dispatchers.IO) {
        val addedRoom = repo.addRoom(room)
        localRepo.setRoom(addedRoom)
    }
}