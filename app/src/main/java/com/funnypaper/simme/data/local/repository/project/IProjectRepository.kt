package com.funnypaper.simme.data.local.repository.project

import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import kotlinx.coroutines.flow.Flow

interface IProjectRepository {
    fun getAllProjects(): Flow<List<ProjectEntity>>
    fun getProjectRelation(id: Int): Flow<ProjectRelation>
    suspend fun updateProject(value: ProjectEntity)
    suspend fun insertProject(value: ProjectEntity)
    suspend fun deleteProject(value: ProjectEntity)
}