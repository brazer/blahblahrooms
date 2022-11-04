package com.softeq.blahblahrooms.domain.di

import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCase
import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCaseImpl
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCase
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCaseImpl
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@dagger.Module
@InstallIn(ViewModelComponent::class)
abstract class BindingModule {

    @Binds
    abstract fun bindFetchRoomsUseCase(useCase: FetchRoomsUseCaseImpl): FetchRoomsUseCase

    @Binds
    abstract fun bindAddRoomUseCase(useCase: AddRoomUseCaseImpl): AddRoomUseCase

}