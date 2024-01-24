package com.funnypaper.simme.di

import android.content.Context
import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.domain.usecase.GetAudioFileUseCase
import com.funnypaper.simme.domain.usecase.GetTimingUseCase
import com.funnypaper.simme.domain.usecase.UpdateAudioFileUseCase
import com.funnypaper.simme.domain.usecase.UpdateTimingUseCase
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

    @Provides
    fun provideGetTimingUseCase(timingRepository: IDataTimingRepository) =
        GetTimingUseCase(timingRepository)

    @Provides
    fun provideUpdateTimingUseCase(timingRepository: IDataTimingRepository) =
        UpdateTimingUseCase(timingRepository)
}