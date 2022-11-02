package com.softeq.blahblahrooms.data.di

import com.softeq.blahblahrooms.data.repo.RoomsRepoImpl
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@dagger.Module
@InstallIn(ViewModelComponent::class)
abstract class BindingModule {

    @Binds
    abstract fun bindRoomsRepo(roomsRepo: RoomsRepoImpl): RoomsRepo

}