package com.funnypaper.simme.data.local.repository.spline

import com.funnypaper.simme.data.local.entity.SplineEntity
import com.funnypaper.simme.data.local.relation.SplineRelation
import kotlinx.coroutines.flow.Flow

interface IDataSplineRepository {
    fun getAllSplines(): Flow<List<SplineEntity>>
    fun getSpline(id: Int): Flow<SplineEntity>
    fun getSplineRelation(id: Int): Flow<SplineRelation>
    suspend fun updateSpline(value: SplineEntity)
    suspend fun insertSpline(value: SplineEntity): Long
    suspend fun deleteSpline(value: SplineEntity)
}