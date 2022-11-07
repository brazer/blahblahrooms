package com.softeq.blahblahrooms.domain.di

import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCase
import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCaseImpl
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCase
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCaseImpl
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@dagger.Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    @Binds
    abstract fun bindFetchRoomsUseCase(useCase: FetchRoomsUseCaseImpl): FetchRoomsUseCase

    @Binds
    abstract fun bindAddRoomUseCase(useCase: AddRoomUseCaseImpl): AddRoomUseCase
}