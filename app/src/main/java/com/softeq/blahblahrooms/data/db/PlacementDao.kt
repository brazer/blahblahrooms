package com.softeq.blahblahrooms.data.db

import androidx.room.*
import com.softeq.blahblahrooms.data.model.PlacementDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacementDao {

    @Query("SELECT * FROM placements")
    fun getPlacements(): Flow<List<PlacementDTO>>

    @Query("SELECT * FROM placements WHERE userId=:userId")
    fun getPlacementByUserId(userId: String): Flow<List<PlacementDTO>>

    @Query("SELECT * FROM placements WHERE id=:id")
    fun getPlacementById(id: Int): Flow<PlacementDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlacements(placements: List<PlacementDTO>)

    @Insert()
    fun insertPlacement(placement: PlacementDTO)

    @Update
    fun updatePlacement(placement: PlacementDTO)

    @Delete
    fun deletePlacement(placement: PlacementDTO)
}