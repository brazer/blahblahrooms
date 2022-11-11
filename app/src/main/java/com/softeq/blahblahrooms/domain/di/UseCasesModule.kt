package com.softeq.blahblahrooms.domain.di

import android.content.Context
import com.softeq.blahblahrooms.domain.repositories.RoomsLocalRepo
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import com.softeq.blahblahrooms.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCasesModule {

    @Provides
    fun provideGetUserIdUseCase(
        userIdRepo: UserIdRepo
    ): GetUserIdUseCase {
        return GetUserIdUseCaseImpl(userIdRepo)
    }

    @Provides
    fun provideSetUserIdUseCase(
        userIdRepo: UserIdRepo
    ): SetUserIdUseCase {
        return SetUserIdUseCaseImpl(userIdRepo)
    }

    @Provides
    fun provideUpdateRoomUseCase(
        roomsRepo: RoomsRepo,
        roomsLocalRepo: RoomsLocalRepo
    ): UpdateRoomUseCase {
        return UpdateRoomUseCaseImpl(roomsRepo, roomsLocalRepo)
    }

    @Provides
    fun provideLoadRoomsUseCase(
        roomsRepo: RoomsRepo,
        roomsLocalRepo: RoomsLocalRepo
    ): LoadRoomsUseCase {
        return LoadRoomsUseCaseImpl(roomsRepo, roomsLocalRepo)
    }

    @Provides
    fun provideGetRoomsUseCase(
        roomsLocalRepo: RoomsLocalRepo
    ): GetRoomsUseCase {
        return GetRoomsUseCaseImpl(roomsLocalRepo)
    }

    @Provides
    fun provideAddRoomUseCase(
        roomsRepo: RoomsRepo,
        roomsLocalRepo: RoomsLocalRepo
    ): AddRoomUseCase {
        return AddRoomUseCaseImpl(roomsRepo, roomsLocalRepo)
    }

    @Provides
    fun provideGetRoomsByUserIdUseCase(
        roomsLocalRepo: RoomsLocalRepo,
        userIdRepo: UserIdRepo
    ): GetRoomsByUserIdUseCase {
        return GetRoomsByUserIdUseCaseImpl(
            roomsLocalRepo,
            userIdRepo
        )
    }

    @Provides
    fun provideGetRoomByIdUseCase(roomsLocalRepo: RoomsLocalRepo): GetRoomByIdUseCase {
        return GetRoomByIdUseCaseImpl(
            roomsLocalRepo
        )
    }

    @Provides
    fun provideGetRoomsByFiltersUseCase(roomsLocalRepo: RoomsLocalRepo): GetRoomsByFiltersUseCase {
        return GetRoomsByFiltersUseCaseImpl(
            roomsLocalRepo
        )
    }

    @Provides
    fun provideGetUserCityLocationUseCase(
        @ApplicationContext context: Context
    ): GetUserCityLocationUseCase {
        return GetUserCityLocationUseCaseImpl(
            context = context
        )
    }
}