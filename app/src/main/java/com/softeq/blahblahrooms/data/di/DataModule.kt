package com.softeq.blahblahrooms.data.di

import android.content.Context
import com.softeq.blahblahrooms.data.preferences.AppPreferences
import com.softeq.blahblahrooms.data.repo.RoomsRepoImpl
import com.softeq.blahblahrooms.data.repo.UserIdRepoImpl
import com.softeq.blahblahrooms.domain.repositories.RoomsRepo
import com.softeq.blahblahrooms.domain.repositories.UserIdRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideRoomsRepo(): RoomsRepo {
        return RoomsRepoImpl()
    }
}