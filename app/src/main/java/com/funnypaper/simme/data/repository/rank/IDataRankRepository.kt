package com.funnypaper.simme.data.repository.rank

import com.funnypaper.simme.data.local.entity.RankEntity
import kotlinx.coroutines.flow.Flow

interface IDataRankRepository {
    fun getAllRanks(): Flow<List<RankEntity>>
    fun getRank(id: Int): Flow<RankEntity>
    suspend fun updateRank(value: RankEntity)
    suspend fun insertRank(value: RankEntity): Long
    suspend fun deleteRank(value: RankEntity)
}