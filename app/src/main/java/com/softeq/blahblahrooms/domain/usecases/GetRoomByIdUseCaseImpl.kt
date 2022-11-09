package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetRoomByIdUseCaseImpl(
    private val localRepo: RoomsLocalRepo
) : GetRoomByIdUseCase {
    override suspend fun invoke(roomId: Int): Flow<Room> = withContext(Dispatchers.IO) {
        return@withContext localRepo.getRoomById(roomId)
    }
}