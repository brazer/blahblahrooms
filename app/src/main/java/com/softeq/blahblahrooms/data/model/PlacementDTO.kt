package com.softeq.blahblahrooms.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room

@Entity(tableName = "placements")
data class PlacementDTO(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "type")
    val type: Int = 0,
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "contacts")
    val contacts: String = "",
    @ColumnInfo(name = "coordinates")
    val coordinates: Double = 0.0,
    @ColumnInfo(name = "address")
    val address: String = "",
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "price")
    val price: Double = 0.0,
    @ColumnInfo(name = "active")
    val active: Boolean = true,
    @ColumnInfo(name = "period")
    val period: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: String = ""
)

fun PlacementDTO.asRoom(): Room {
    return Room(
        id = this.id,
        userId = this.userId,
        price = this.price.toFloat(),
        location = LatLng(this.coordinates, 0.0),
        address = this.address,
        period = if (this.period == 0) Period.SHORT else Period.LONG,
        description = this.description,
        email = this.contacts
    )
}

fun Room.asPlacementDTO(): PlacementDTO {
    return PlacementDTO(
        id = this.id,
        type = 0,
        description = this.description,
        contacts = this.email,
        coordinates = this.location.latitude,
        address = this.address,
        city = "",
        price = this.price.toDouble(),
        active = true,
        period = if (this.period == Period.SHORT) 0 else 1,
        userId = this.userId
    )
}