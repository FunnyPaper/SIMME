package com.funnypaper.simme.di

import android.content.Context
import androidx.room.Room
import com.funnypaper.simme.data.local.SIMMEDatabase
import com.funnypaper.simme.data.local.dao.BoardDao
import com.funnypaper.simme.data.local.dao.NoteDao
import com.funnypaper.simme.data.local.dao.ProjectDao
import com.funnypaper.simme.data.local.dao.RankDao
import com.funnypaper.simme.data.local.dao.SplineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SIMMEDatabase
        = Room.databaseBuilder(
            context,
            SIMMEDatabase::class.java,
            SIMMEDatabase.NAME
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideBoardDao(database: SIMMEDatabase): BoardDao =
        database.boardDao()

    @Provides
    fun provideNoteDao(database: SIMMEDatabase): NoteDao =
        database.noteDao()

    @Provides
    fun provideProjectDao(database: SIMMEDatabase): ProjectDao =
        database.projectDao()

    @Provides
    fun provideRankDao(database: SIMMEDatabase): RankDao =
        database.rankDao()

    @Provides
    fun provideSplineDao(database: SIMMEDatabase): SplineDao =
        database.splineDao()
}