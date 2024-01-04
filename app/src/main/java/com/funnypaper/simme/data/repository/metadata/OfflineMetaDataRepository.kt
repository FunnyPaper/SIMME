package com.funnypaper.simme.data.repository.metadata

import com.funnypaper.simme.data.local.dao.MetaDataDao
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineMetaDataRepository @Inject constructor(
    private val metaDataDao: MetaDataDao
) : IMetaDataRepository {
    override fun getMetaData(id: Int): Flow<MetaDataEntity> =
        metaDataDao.getMetaDataById(id)
    override fun getAllMetaData(id: Int): Flow<List<MetaDataEntity>> =
        metaDataDao.getAllMetaData()
    override fun getAllMetaDataForProject(id: Int): Flow<List<MetaDataEntity>> =
        metaDataDao.getAllMetaDataByProjectId(id)
    override fun getAllMetaDataForNote(id: Int): Flow<List<MetaDataEntity>> =
        metaDataDao.getAllMetaDataByNoteId(id)
    override suspend fun updateMetaData(value: MetaDataEntity) =
        metaDataDao.update(value)
    override suspend fun insertMetaData(value: MetaDataEntity): Long =
        metaDataDao.insert(value)
    override suspend fun deleteMetaData(value: MetaDataEntity) =
        metaDataDao.delete(value)
}