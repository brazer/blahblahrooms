package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetUserIdUseCaseImpl(
    private val userIdRepo: UserIdRepo
) : GetUserIdUseCase {
    override suspend fun invoke(): String? = withContext(Dispatchers.IO) {
        return@withContext userIdRepo.getUserId().first()
    }
}