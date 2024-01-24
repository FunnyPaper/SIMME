package com.funnypaper.simme.data.repository.timing

import com.funnypaper.simme.data.local.entity.TimingEntity
import kotlinx.coroutines.flow.Flow

interface IDataTimingRepository {
    fun getTiming(id: Int): Flow<TimingEntity>
    suspend fun updateTiming(value: TimingEntity)
    suspend fun insertTiming(value: TimingEntity): Long
    suspend fun deleteTiming(value: TimingEntity)
    fun getTimingByProjectId(id: Int): Flow<TimingEntity?>
}