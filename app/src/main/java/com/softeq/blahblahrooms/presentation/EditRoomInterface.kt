package com.softeq.blahblahrooms.presentation

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.data.model.Period

interface EditRoomInterface {
    fun roomPriceChanged(price: Float)
    fun roomLocationChanged(location: LatLng)
    fun roomAddressChanged(address: String)
    fun roomDescriptionChanged(description: String)
    fun roomPeriodChanged(period: Period)
    fun roomEmailChanged(email: String)
}