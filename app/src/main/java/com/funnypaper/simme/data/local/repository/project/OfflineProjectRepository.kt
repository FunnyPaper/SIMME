package com.funnypaper.simme.data.local.repository.project

import com.funnypaper.simme.data.local.dao.ProjectDao
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineProjectRepository @Inject constructor(
    private val projectDao: ProjectDao
) : IProjectRepository {
    override fun getAllProjects(): Flow<List<ProjectEntity>> =
        projectDao.getProjects()
    override fun getProjectRelation(id: Int): Flow<ProjectRelation> =
        projectDao.getProjectRelationById(id)
    override suspend fun updateProject(value: ProjectEntity) =
        projectDao.update(value)
    override suspend fun insertProject(value: ProjectEntity) =
        projectDao.insert(value)
    override suspend fun deleteProject(value: ProjectEntity) =
        projectDao.delete(value)
}