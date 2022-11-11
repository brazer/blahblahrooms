package com.softeq.blahblahrooms.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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