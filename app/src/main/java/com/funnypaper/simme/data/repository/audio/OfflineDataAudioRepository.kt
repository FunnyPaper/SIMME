package com.funnypaper.simme.data.repository.audio

import com.funnypaper.simme.data.local.dao.AudioDao
import com.funnypaper.simme.data.local.entity.AudioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDataAudioRepository @Inject constructor(
    private val audioDao: AudioDao
) : IDataAudioRepository {
    override fun getAudio(id: Int): Flow<AudioEntity> =
        audioDao.getAudioById(id)
    override suspend fun updateAudio(value: AudioEntity) =
        audioDao.update(value)
    override suspend fun insertAudio(value: AudioEntity): Long =
        audioDao.insert(value)
    override suspend fun deleteAudio(value: AudioEntity) =
        audioDao.delete(value)
}