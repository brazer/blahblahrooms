package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SetUserIdUseCaseImpl(
    private val userIdRepo: UserIdRepo
) : SetUserIdUseCase {
    override suspend fun invoke(): Unit = withContext(Dispatchers.IO) {
        userIdRepo.getUserId().collect() {
            if (it == null) {
                userIdRepo.setUserId(UUID.randomUUID().toString())
            }
        }
    }
}