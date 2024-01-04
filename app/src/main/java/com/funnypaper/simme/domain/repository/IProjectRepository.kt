package com.funnypaper.simme.domain.repository

import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import kotlinx.coroutines.flow.Flow

// TODO: Change relation to model

interface IProjectRepository {
    fun getAllProjects(): Flow<List<ProjectEntity>>
    fun getProjectRelation(id: Int): Flow<ProjectRelation>
    // TODO: Should take form data co create project
    suspend fun createProject()
    suspend fun importProject(projectRelation: ProjectRelation): Int
    suspend fun duplicateProject(id: Int): Int
    suspend fun deleteProject(id: Int)
}