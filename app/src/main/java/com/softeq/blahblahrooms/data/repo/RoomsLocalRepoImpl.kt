package com.softeq.blahblahrooms.data.repo

import com.softeq.blahblahrooms.data.db.PlacementDao
import com.softeq.blahblahrooms.data.mappers.asPlacementDTO
import com.softeq.blahblahrooms.data.mappers.asRoom
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomsLocalRepoImpl(
    private val placementDao: PlacementDao
) : RoomsLocalRepo {
    override fun getRooms(): Flow<List<Room>> {
        return placementDao.getPlacements().map { placements ->
            placements.map {
                it.asRoom()
            }
        }
    }

    override fun getRoomsByUserId(userId: String): Flow<List<Room>> {
        return placementDao.getPlacementByUserId(userId).map { placements ->
            placements.map {
                it.asRoom()
            }
        }
    }

    override fun getRoomById(roomId: Int): Flow<Room> {
        return placementDao.getPlacementById(roomId).map { it.asRoom() }
    }

    override fun setRooms(rooms: List<Room>) {
        placementDao.insertAllPlacements(rooms.map { it.asPlacementDTO() })
    }

    override fun setRoom(room: Room) {
        placementDao.insertPlacement(room.asPlacementDTO())
    }

    override fun updateRoom(room: Room) {
        placementDao.updatePlacement(room.asPlacementDTO())
    }

    override fun delete(room: Room) {
        placementDao.deletePlacement(room.asPlacementDTO())
    }
}