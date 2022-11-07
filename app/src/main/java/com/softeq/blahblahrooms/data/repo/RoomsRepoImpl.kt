package com.softeq.blahblahrooms.data.repo

import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RoomsRepoImpl @Inject constructor() : RoomsRepo {

    override suspend fun getRooms(): Flow<List<Room>> = flow {
        //todo: get from api
        delay(3000)
        emit(listOf(
            Room(1, "1", 100f, LatLng(53.922818, 27.616076), "Address here", Period.LONG, "Description", "pupkin@tut.by"),
            Room(2, "2", 120f, LatLng(53.888756, 27.504955), "Address here", Period.SHORT, "Description", "pup@tut.by"),
            Room(3, "3", 99.99f, LatLng(53.907490, 27.598443), "Address here", Period.SHORT, "Description", "pup@tut.by"),
            Room(4,"4", 1f, LatLng(53.860541, 27.623315), "Address here", Period.SHORT, "Description", "pup@tut.by"),
            Room(5,"5", 12000f, LatLng(53.923807, 27.469993), "Address here", Period.SHORT, "Description", "pup@tut.by")
        ))
    }

    override suspend fun addRoom(room: Room): Flow<Unit> = flow {
        //todo: use api
        delay(3000)
        val error = Random.nextInt(100) <= 50
        if (error) {
            throw RuntimeException()
        } else emit(Unit)
    }

    override suspend fun updateRoom(room: Room) {
        //todo use api
        runBlocking {
            delay(3000)
        }
    }

}