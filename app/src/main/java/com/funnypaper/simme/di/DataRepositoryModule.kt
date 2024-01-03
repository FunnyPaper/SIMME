package com.funnypaper.simme.di

import com.funnypaper.simme.data.local.repository.board.IDataBoardRepository
import com.funnypaper.simme.data.local.repository.board.OfflineDataBoardRepository
import com.funnypaper.simme.data.local.repository.metadata.IMetaDataRepository
import com.funnypaper.simme.data.local.repository.metadata.OfflineMetaDataRepository
import com.funnypaper.simme.data.local.repository.note.IDataNoteRepository
import com.funnypaper.simme.data.local.repository.note.OfflineDataNoteRepository
import com.funnypaper.simme.data.local.repository.project.IDataProjectRepository
import com.funnypaper.simme.data.local.repository.project.OfflineDataProjectRepository
import com.funnypaper.simme.data.local.repository.rank.IDataRankRepository
import com.funnypaper.simme.data.local.repository.rank.OfflineDataRankRepository
import com.funnypaper.simme.data.local.repository.spline.IDataSplineRepository
import com.funnypaper.simme.data.local.repository.spline.OfflineDataSplineRepository
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
}
