package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MetaDataDao : ICRUDDao<MetaDataEntity> {
    @Query("SELECT * FROM meta_datas")
    fun getAllMetaData(): Flow<List<MetaDataEntity>>

    @Query("SELECT * FROM meta_datas WHERE project_id = :id")
    fun getAllMetaDataByProjectId(id: Int): Flow<List<MetaDataEntity>>

    @Query("SELECT * FROM meta_datas WHERE note_id = :id")
    fun getAllMetaDataByNoteId(id: Int): Flow<List<MetaDataEntity>>

    @Query("SELECT * FROM meta_datas WHERE id = :id")
    fun getMetaDataById(id: Int): Flow<MetaDataEntity>
}