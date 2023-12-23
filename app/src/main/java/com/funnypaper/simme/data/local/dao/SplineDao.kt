package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.SplineEntity
import com.funnypaper.simme.data.local.relation.SplineRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface SplineDao : ICRUDDao<SplineEntity> {
    @Query("SELECT * FROM splines WHERE id = :value")
    fun getSplineById(value: Int): Flow<SplineEntity>

    @Transaction
    @Query("SELECT * FROM splines WHERE id = :value")
    fun getSplineRelationById(value: Int): Flow<SplineRelation>

    @Query("SELECT * FROM splines")
    fun getSplines(): Flow<List<SplineEntity>>
}