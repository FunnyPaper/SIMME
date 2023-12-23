package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation

@Dao
interface ProjectDao : ICRUDDao<ProjectEntity> {
    @Query("SELECT * FROM projects WHERE id = :value")
    suspend fun getProjectById(value: Int): ProjectEntity

    @Transaction
    @Query("SELECT * FROM projects WHERE id = :value")
    suspend fun getProjectRelationById(value: Int): ProjectRelation

    @Query("SELECT * FROM projects")
    suspend fun getProjects(): List<ProjectEntity>
}