package com.funnypaper.simme.data.repository.audio

import com.funnypaper.simme.data.local.dao.AudioDao
import com.funnypaper.simme.data.local.dao.ProjectDao
import com.funnypaper.simme.data.local.entity.AudioEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineAudioRepository @Inject constructor(
    private val audioDao: AudioDao,
    private val projectDao: ProjectDao,
) : IDataAudioRepository {
    override fun getAudio(id: Int): Flow<AudioEntity> =
        audioDao.getAudioById(id)

    override fun getAudioByProjectId(id: Int): Flow<AudioEntity?> =
        projectDao.getProjectRelationById(id).map { it.audio }

    override suspend fun updateAudio(value: AudioEntity) =
        audioDao.update(value)

    override suspend fun insertAudio(value: AudioEntity): Long =
        audioDao.insert(value)

    override suspend fun deleteAudio(value: AudioEntity) =
        audioDao.delete(value)

    override suspend fun deleteAudioByProjectId(id: Int) {
        val relation = projectDao.getProjectRelationById(id).first()
        relation.audio?.let {
            audioDao.delete(it)
        }
    }
}