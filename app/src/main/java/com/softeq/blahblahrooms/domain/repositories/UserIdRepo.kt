package com.softeq.blahblahrooms.domain.repositories

import kotlinx.coroutines.flow.Flow

interface UserIdRepo {
    suspend fun getUserId(): Flow<String?>
    suspend fun setUserId(userId: String)
}