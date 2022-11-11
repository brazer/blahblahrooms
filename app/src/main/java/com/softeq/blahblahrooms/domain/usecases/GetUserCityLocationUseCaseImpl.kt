package com.softeq.blahblahrooms.domain.usecases

import android.content.Context
import com.softeq.blahblahrooms.data.providers.CurrentLocationProvider

class GetUserCityLocationUseCaseImpl(
    private val context: Context
) : GetUserCityLocationUseCase {
    override fun invoke(): String? {
        var city: String? = null
        CurrentLocationProvider.getCity(context) { currentCity ->
            city = currentCity
        }
        return city
    }
}