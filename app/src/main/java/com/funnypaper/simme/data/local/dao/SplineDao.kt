package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.SplineEntity
import com.funnypaper.simme.data.local.relation.SplineRelation

@Dao
interface SplineDao : ICRUDDao<SplineEntity> {
    @Query("SELECT * FROM splines WHERE id = :value")
    suspend fun getSplineById(value: Int): SplineEntity

    @Transaction
    @Query("SELECT * FROM splines WHERE id = :value")
    suspend fun getSplineRelationById(value: Int): SplineRelation

    @Query("SELECT * FROM splines")
    suspend fun getSplines(): List<SplineEntity>
}