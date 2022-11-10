package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRoomsUseCaseImpl @Inject constructor(
    private val localRepo: RoomsLocalRepo
) : GetRoomsUseCase {
    override suspend fun invoke(): Flow<List<Room>> = withContext(Dispatchers.IO) {
        return@withContext localRepo.getRooms()
    }
}