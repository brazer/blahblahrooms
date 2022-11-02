package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRoomsUseCaseImpl @Inject constructor(
    private val repo: RoomsRepo
) : FetchRoomsUseCase  {
    override suspend fun fetch(): Flow<List<Room>> {
        return repo.getRooms()
    }
}