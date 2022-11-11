package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.data.model.Period
import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.coroutines.flow.Flow

interface GetRoomsByFiltersUseCase {
    suspend fun invoke(
        period: Period?,
        city: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Flow<List<Room>>
}