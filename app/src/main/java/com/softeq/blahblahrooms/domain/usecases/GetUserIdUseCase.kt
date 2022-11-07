package com.softeq.blahblahrooms.domain.usecases

interface GetUserIdUseCase {
    suspend fun invoke(): String?
}