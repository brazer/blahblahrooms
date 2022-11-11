package com.softeq.blahblahrooms.domain.usecases

import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetRoomsByFiltersUseCaseImpl(
    private val localRepo: RoomsLocalRepo
) : GetRoomsByFiltersUseCase {
    override suspend fun invoke(
        period: Period?,
        city: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Flow<List<Room>> = withContext(Dispatchers.IO) {
        return@withContext localRepo.getRoomsByFilters(
            period = period,
            city = city,
            minPrice = minPrice,
            maxPrice = maxPrice
        )
    }
}
