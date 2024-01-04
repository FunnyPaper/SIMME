package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao : ICRUDDao<ProjectEntity> {
    @Query("SELECT * FROM project WHERE id = :value")
    fun getProjectById(value: Int): Flow<ProjectEntity>

    @Transaction
    @Query("SELECT * FROM project WHERE id = :value")
    fun getProjectRelationById(value: Int): Flow<ProjectRelation>

    @Query("SELECT * FROM project")
    fun getAllProjects(): Flow<List<ProjectEntity>>
}