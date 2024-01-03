package com.funnypaper.simme.data.local.repository.rank

import com.funnypaper.simme.data.local.dao.RankDao
import com.funnypaper.simme.data.local.entity.RankEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDataRankRepository @Inject constructor(
    private val rankDao: RankDao
) : IDataRankRepository {
    override fun getAllRanks(): Flow<List<RankEntity>> =
        rankDao.getAllRanks()
    override fun getRank(id: Int): Flow<RankEntity> =
        rankDao.getRankById(id)
    override suspend fun updateRank(value: RankEntity) =
        rankDao.update(value)
    override suspend fun insertRank(value: RankEntity): Long =
        rankDao.insert(value)
    override suspend fun deleteRank(value: RankEntity) =
        rankDao.delete(value)
}