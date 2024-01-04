package com.funnypaper.simme.di

import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.data.repository.audio.OfflineDataAudioRepository
import com.funnypaper.simme.data.repository.board.IDataBoardRepository
import com.funnypaper.simme.data.repository.board.OfflineDataBoardRepository
import com.funnypaper.simme.data.repository.metadata.IMetaDataRepository
import com.funnypaper.simme.data.repository.metadata.OfflineMetaDataRepository
import com.funnypaper.simme.data.repository.note.IDataNoteRepository
import com.funnypaper.simme.data.repository.note.OfflineDataNoteRepository
import com.funnypaper.simme.data.repository.project.IDataProjectRepository
import com.funnypaper.simme.data.repository.project.OfflineDataProjectRepository
import com.funnypaper.simme.data.repository.rank.IDataRankRepository
import com.funnypaper.simme.data.repository.rank.OfflineDataRankRepository
import com.funnypaper.simme.data.repository.spline.IDataSplineRepository
import com.funnypaper.simme.data.repository.spline.OfflineDataSplineRepository
import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.data.repository.timing.OfflineDataTimingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataRepositoryBindsModule {
    @Binds
    abstract fun bindOfflineDataProjectRepository(repository: OfflineDataProjectRepository): IDataProjectRepository

    @Binds
    abstract fun bindOfflineDataBoardRepository(repository: OfflineDataBoardRepository): IDataBoardRepository

    @Binds
    abstract fun bindOfflineMetaDataRepository(repository: OfflineMetaDataRepository): IMetaDataRepository

    @Binds
    abstract fun bindOfflineDataRankRepository(repository: OfflineDataRankRepository): IDataRankRepository

    @Binds
    abstract fun bindOfflineSplineRepository(repository: OfflineDataSplineRepository): IDataSplineRepository

    @Binds
    abstract fun bindOfflineDataNoteRepository(repository: OfflineDataNoteRepository): IDataNoteRepository

    @Binds
    abstract fun bindOfflineDataAudioRepository(repository: OfflineDataAudioRepository): IDataAudioRepository

    @Binds
    abstract fun bindOfflineDataTimingRepository(repository: OfflineDataTimingRepository): IDataTimingRepository
}
