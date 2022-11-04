package com.softeq.blahblahrooms.presentation

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.domain.models.Period

interface EditRoomInterface {
    fun onPriceChanged(price: Float)
    fun onLocationChanged(location: LatLng)
    fun onAddressChanged(address: String)
    fun onDescriptionChanged(description: String)
    fun onPeriodChanged(period: Period)
    fun onEmailChanged(email: String)
}