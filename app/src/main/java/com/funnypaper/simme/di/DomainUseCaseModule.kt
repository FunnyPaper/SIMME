package com.funnypaper.simme.di

import android.content.Context
import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.domain.usecase.GetAudioFileUseCase
import com.funnypaper.simme.domain.usecase.UpdateAudioFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainUseCaseModule {
    @Provides
    fun provideGetAudioFileUseCase(
        @ApplicationContext context: Context,
        audioRepository: IDataAudioRepository,
    ) =
        GetAudioFileUseCase(context, audioRepository)

    @Provides
    fun provideUpdateAudioFileUseCase(
        @ApplicationContext context: Context,
        audioRepository: IDataAudioRepository,
    ) =
        UpdateAudioFileUseCase(context, audioRepository)
}