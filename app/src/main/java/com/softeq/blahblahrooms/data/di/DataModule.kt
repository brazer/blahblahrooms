package com.softeq.blahblahrooms.data.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.softeq.blahblahrooms.data.DataConfig
import com.softeq.blahblahrooms.data.api.RoomService
import com.softeq.blahblahrooms.data.db.PlacementDao
import com.softeq.blahblahrooms.data.db.PlacementDatabase
import com.softeq.blahblahrooms.data.preferences.AppPreferences
import com.softeq.blahblahrooms.data.repo.RoomsLocalRepoImpl
import com.softeq.blahblahrooms.data.repo.RoomsRepoImpl
import com.softeq.blahblahrooms.data.repo.UserIdRepoImpl
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideAppReferences(
        @ApplicationContext appContext: Context
    ): AppPreferences {
        return AppPreferences(appContext)
    }

    @Provides
    @Singleton
    fun provideUserIdRepo(
        appPreferences: AppPreferences
    ): UserIdRepo {
        return UserIdRepoImpl(appPreferences)
    }

    @Provides
    @Singleton
    fun provideRoomsRepo(
        roomService: RoomService
    ): RoomsRepo {
        return RoomsRepoImpl(roomService)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json {
                encodeDefaults = true
            }.asConverterFactory("application/json".toMediaType()))
            .baseUrl(DataConfig.BASE_URL_ROOM_API)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomService(retrofit: Retrofit): RoomService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providePlacementDatabase(@ApplicationContext appContext: Context): PlacementDatabase {
        return Room.databaseBuilder(
            appContext,
            PlacementDatabase::class.java,
            DataConfig.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlacementDao(database: PlacementDatabase): PlacementDao {
        return database.placementDao()
    }

    @Provides
    @Singleton
    fun provideRoomLocalRepo(placementDao: PlacementDao): RoomsLocalRepo {
        return RoomsLocalRepoImpl(placementDao = placementDao)
    }
}