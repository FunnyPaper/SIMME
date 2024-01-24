package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.funnypaper.simme.data.local.entity.TimingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimingDao : ICRUDDao<TimingEntity>  {
    @Query("SELECT * FROM timing WHERE id = :value")
    fun getTimingById(value: Int): Flow<TimingEntity>

    @Query("SELECT * FROM timing")
    fun getAllTimings(): Flow<List<TimingEntity>>

    @Query("SELECT * FROM timing WHERE project_id = :value")
    fun getTimingByProjectId(value: Int): Flow<TimingEntity?>
}