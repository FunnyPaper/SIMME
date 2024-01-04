package com.funnypaper.simme.data.repository.timing

import com.funnypaper.simme.data.local.dao.TimingDao
import com.funnypaper.simme.data.local.entity.TimingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineTimingRepository @Inject constructor(
    private val timingDao: TimingDao
) : IDataTimingRepository {
    override fun getTiming(id: Int): Flow<TimingEntity> =
        timingDao.getTimingById(id)
    override suspend fun updateTiming(value: TimingEntity) =
        timingDao.update(value)
    override suspend fun insertTiming(value: TimingEntity): Long =
        timingDao.insert(value)
    override suspend fun deleteTiming(value: TimingEntity) =
        timingDao.delete(value)
}