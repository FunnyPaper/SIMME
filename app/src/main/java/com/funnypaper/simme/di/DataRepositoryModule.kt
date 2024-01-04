package com.funnypaper.simme.di

import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.data.repository.audio.OfflineAudioRepository
import com.funnypaper.simme.data.repository.board.IDataBoardRepository
import com.funnypaper.simme.data.repository.board.OfflineBoardRepository
import com.funnypaper.simme.data.repository.metadata.IMetaDataRepository
import com.funnypaper.simme.data.repository.metadata.OfflineMetaDataRepository
import com.funnypaper.simme.data.repository.note.IDataNoteRepository
import com.funnypaper.simme.data.repository.note.OfflineNoteRepository
import com.funnypaper.simme.data.repository.project.IDataProjectRepository
import com.funnypaper.simme.data.repository.project.OfflineProjectRepository
import com.funnypaper.simme.data.repository.rank.IDataRankRepository
import com.funnypaper.simme.data.repository.rank.OfflineRankRepository
import com.funnypaper.simme.data.repository.spline.IDataSplineRepository
import com.funnypaper.simme.data.repository.spline.OfflineSplineRepository
import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.data.repository.timing.OfflineTimingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataRepositoryBindsModule {
    @Binds
    abstract fun bindOfflineDataProjectRepository(repository: OfflineProjectRepository): IDataProjectRepository

    @Binds
    abstract fun bindOfflineDataBoardRepository(repository: OfflineBoardRepository): IDataBoardRepository

    @Binds
    abstract fun bindOfflineMetaDataRepository(repository: OfflineMetaDataRepository): IMetaDataRepository

    @Binds
    abstract fun bindOfflineDataRankRepository(repository: OfflineRankRepository): IDataRankRepository

    @Binds
    abstract fun bindOfflineSplineRepository(repository: OfflineSplineRepository): IDataSplineRepository

    @Binds
    abstract fun bindOfflineDataNoteRepository(repository: OfflineNoteRepository): IDataNoteRepository

    @Binds
    abstract fun bindOfflineDataAudioRepository(repository: OfflineAudioRepository): IDataAudioRepository

    @Binds
    abstract fun bindOfflineDataTimingRepository(repository: OfflineTimingRepository): IDataTimingRepository
}
