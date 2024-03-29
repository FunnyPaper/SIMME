package com.funnypaper.simme.data.repository.audio

import com.funnypaper.simme.data.local.entity.AudioEntity
import kotlinx.coroutines.flow.Flow

interface IDataAudioRepository {
    fun getAudio(id: Int): Flow<AudioEntity>
    fun getAudioByProjectId(id: Int): Flow<AudioEntity?>
    suspend fun updateAudio(value: AudioEntity)
    suspend fun insertAudio(value: AudioEntity): Long
    suspend fun deleteAudio(value: AudioEntity)
    suspend fun deleteAudioByProjectId(id: Int)
}