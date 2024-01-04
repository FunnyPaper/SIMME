package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.AudioEntity
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.relation.BoardRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao : ICRUDDao<AudioEntity>  {
    @Query("SELECT * FROM audio WHERE id = :value")
    fun getAudioById(value: Int): Flow<AudioEntity>

    @Query("SELECT * FROM audio")
    fun getAllAudios(): Flow<List<AudioEntity>>
}