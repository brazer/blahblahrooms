package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddRoomUseCaseImpl @Inject constructor(
    private val repo: RoomsRepo
) : AddRoomUseCase {
    override suspend fun add(room: Room): Flow<Unit> = withContext(Dispatchers.IO) {
        return@withContext repo.addRoom(room)
    }
}