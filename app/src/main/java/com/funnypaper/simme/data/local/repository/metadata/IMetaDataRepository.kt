package com.funnypaper.simme.data.local.repository.metadata

import com.funnypaper.simme.data.local.entity.MetaDataEntity
import kotlinx.coroutines.flow.Flow

interface IMetaDataRepository {
    fun getMetaData(id: Int): Flow<MetaDataEntity>
    fun getAllMetaData(id: Int): Flow<List<MetaDataEntity>>
    fun getAllMetaDataForProject(id: Int): Flow<List<MetaDataEntity>>
    fun getAllMetaDataForNote(id: Int): Flow<List<MetaDataEntity>>
    suspend fun updateMetaData(value: MetaDataEntity)
    suspend fun insertMetaData(value: MetaDataEntity): Long
    suspend fun deleteMetaData(value: MetaDataEntity)
}