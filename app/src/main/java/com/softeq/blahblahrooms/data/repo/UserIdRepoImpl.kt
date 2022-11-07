package com.softeq.blahblahrooms.data.repo

import com.softeq.blahblahrooms.data.preferences.AppPreferences
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import kotlinx.coroutines.flow.Flow

class UserIdRepoImpl(private val appPreferences: AppPreferences) : UserIdRepo {
    override suspend fun getUserId(): Flow<String?> {
        return appPreferences.getUserId()
    }

    override suspend fun setUserId(userId: String) {
        appPreferences.putUserId(userId)
    }
}