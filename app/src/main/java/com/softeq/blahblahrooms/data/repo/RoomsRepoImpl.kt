package com.softeq.blahblahrooms.data.repo

import com.softeq.blahblahrooms.data.api.RoomService
import com.softeq.blahblahrooms.data.exception.ApiException
import com.softeq.blahblahrooms.data.model.asNewPlacement
import com.softeq.blahblahrooms.data.model.asRoom
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo

class RoomsRepoImpl(
    private val api: RoomService
) : RoomsRepo {

    override suspend fun getRooms(): List<Room> {
        return api.getRooms().execute().let { response ->
            if (response.isSuccessful) {
                response.body()!!.map {
                    it.asRoom()
                }
            } else {
                throw ApiException(msg = response.message())
            }
        }
    }

    override suspend fun addRoom(room: Room): Room {
        api.postRoom(room.asNewPlacement()).execute().let { response ->
            if (response.isSuccessful) {
                return response.body()!!.asRoom()
            } else {
                throw ApiException(msg = response.message())
            }
        }
    }

    override suspend fun updateRoom(room: Room): Room {
        api.putRoom(room.id, room.asNewPlacement()).execute().let { response ->
            if (response.isSuccessful) {
                return response.body()!!.asRoom()
            } else {
                throw ApiException(msg = response.message())
            }
        }
    }

    override suspend fun deleteRoom(room: Room) {
        api.deleteRoom(room.id, room.asNewPlacement())
    }
}