package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadRoomsUseCaseImpl(
    private val repo: RoomsRepo,
    private val localRepo: RoomsLocalRepo
) : LoadRoomsUseCase {
    override suspend fun invoke() = withContext(Dispatchers.IO) {
        val rooms = repo.getRooms()
        localRepo.setRooms(rooms)
    }
}