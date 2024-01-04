package com.funnypaper.simme.di

import com.funnypaper.simme.domain.repository.IProjectRepository
import com.funnypaper.simme.domain.repository.ProjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainRepositoryModule {
    @Binds
    abstract fun provideProjectRepository(repository: ProjectRepository): IProjectRepository
}