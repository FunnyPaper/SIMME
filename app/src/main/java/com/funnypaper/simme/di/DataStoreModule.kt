package com.funnypaper.simme.di

import android.content.Context
import com.funnypaper.simme.domain.repository.NavigationPreferencesRepository
import com.funnypaper.simme.domain.utility.context.navigationDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideNavigationPreferencesRepository(@ApplicationContext context: Context) =
        NavigationPreferencesRepository(context.navigationDataStore)
}