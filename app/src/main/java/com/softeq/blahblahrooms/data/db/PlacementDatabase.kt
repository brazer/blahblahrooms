package com.softeq.blahblahrooms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softeq.blahblahrooms.data.model.PlacementDTO

@Database(entities = [PlacementDTO::class], version = 1)
abstract class PlacementDatabase : RoomDatabase() {
    abstract fun placementDao(): PlacementDao
}