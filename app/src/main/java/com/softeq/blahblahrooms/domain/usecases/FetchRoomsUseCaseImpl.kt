package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRoomsUseCaseImpl @Inject constructor(
    private val repo: RoomsRepo
) : FetchRoomsUseCase {
    override suspend fun fetch(): Flow<List<Room>> = withContext(Dispatchers.IO) {
        return@withContext repo.getRooms()
    }
}