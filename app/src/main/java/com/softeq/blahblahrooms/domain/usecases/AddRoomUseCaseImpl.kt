package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRoomUseCaseImpl @Inject constructor(
    private val repo: RoomsRepo
): AddRoomUseCase {
    override suspend fun add(room: Room): Flow<Unit> {
        return repo.addRoom(room)
    }
}