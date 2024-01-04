package com.funnypaper.simme.data.repository.project

import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import kotlinx.coroutines.flow.Flow

interface IDataProjectRepository {
    fun getAllProjects(): Flow<List<ProjectEntity>>
    fun getProject(id: Int): Flow<ProjectEntity>
    fun getProjectRelation(id: Int): Flow<ProjectRelation>
    suspend fun updateProject(value: ProjectEntity)
    suspend fun insertProject(value: ProjectEntity): Long
    suspend fun deleteProject(value: ProjectEntity)
}