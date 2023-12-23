package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.funnypaper.simme.data.local.entity.RankEntity

@Dao
interface RankDao : ICRUDDao<RankEntity> {
    @Query("SELECT * FROM ranks WHERE id = :value")
    suspend fun getRankById(value: Int): RankEntity

    @Query("SELECT * FROM ranks")
    suspend fun getRanks(): List<RankEntity>
}