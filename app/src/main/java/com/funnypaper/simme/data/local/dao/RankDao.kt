package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.funnypaper.simme.data.local.entity.RankEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RankDao : ICRUDDao<RankEntity> {
    @Query("SELECT * FROM ranks WHERE id = :value")
    fun getRankById(value: Int): Flow<RankEntity>

    @Query("SELECT * FROM ranks")
    fun getRanks(): Flow<List<RankEntity>>
}