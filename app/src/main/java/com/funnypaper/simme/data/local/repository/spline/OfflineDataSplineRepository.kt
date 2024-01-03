package com.funnypaper.simme.data.local.repository.spline

import com.funnypaper.simme.data.local.dao.SplineDao
import com.funnypaper.simme.data.local.entity.SplineEntity
import com.funnypaper.simme.data.local.relation.SplineRelation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDataSplineRepository @Inject constructor(
    private val splineDao: SplineDao
): IDataSplineRepository {
    override fun getAllSplines(): Flow<List<SplineEntity>> =
        splineDao.getAllSplines()
    override fun getSpline(id: Int): Flow<SplineEntity> =
        splineDao.getSplineById(id)
    override fun getSplineRelation(id: Int): Flow<SplineRelation> =
        splineDao.getSplineRelationById(id)

    override suspend fun updateSpline(value: SplineEntity) =
        splineDao.update(value)
    override suspend fun insertSpline(value: SplineEntity): Long =
        splineDao.insert(value)
    override suspend fun deleteSpline(value: SplineEntity) =
        splineDao.delete(value)
}