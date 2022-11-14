package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetRoomsByUserIdUseCaseImpl(
    private val localRepo: RoomsLocalRepo,
    private val userIdRepo: UserIdRepo
) : GetRoomsByUserIdUseCase {
    override suspend fun invoke(): Flow<List<Room>> = withContext(Dispatchers.IO) {
        val userId = userIdRepo.getUserId().first()
        return@withContext if (userId == null) {
            flow { emit(emptyList()) }
        } else {
            localRepo.getRoomsByUserId(userId = userId)
        }
    }
}