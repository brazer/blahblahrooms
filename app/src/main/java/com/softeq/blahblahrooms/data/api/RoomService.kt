package com.softeq.blahblahrooms.data.api

import com.softeq.blahblahrooms.data.models.NewPlacement
import com.softeq.blahblahrooms.data.models.PlacementAPI
import retrofit2.Call
import retrofit2.http.*

interface RoomService {
    @GET("./")
    fun getRooms(): Call<List<PlacementAPI>>

    @POST("./")
    fun postRoom(@Body newPlacement: NewPlacement): Call<PlacementAPI>

    @PUT("{id}")
    fun putRoom(@Path("id") id: Int, @Body newPlacement: NewPlacement): Call<PlacementAPI>

    @DELETE("{id}")
    fun deleteRoom(@Path("id") id: Int, @Body newPlacement: NewPlacement)
}