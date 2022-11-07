package com.softeq.blahblahrooms.domain.di

import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import com.softeq.blahblahrooms.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        roomsRepo: RoomsRepo
    ): UpdateRoomUseCase {
        return UpdateRoomUseCaseImpl(roomsRepo)
    }
}