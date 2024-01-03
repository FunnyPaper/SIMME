package com.funnypaper.simme.domain.repository

import android.net.Uri
import android.util.Log
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import com.funnypaper.simme.data.local.repository.board.IDataBoardRepository
import com.funnypaper.simme.data.local.repository.metadata.IMetaDataRepository
import com.funnypaper.simme.data.local.repository.note.IDataNoteRepository
import com.funnypaper.simme.data.local.repository.project.IDataProjectRepository
import com.funnypaper.simme.data.local.repository.rank.IDataRankRepository
import com.funnypaper.simme.data.local.repository.spline.IDataSplineRepository
import com.funnypaper.simme.domain.model.PointModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val dataProjectRepository: IDataProjectRepository,
    private val dataBoardRepository: IDataBoardRepository,
    private val metaDataRepository: IMetaDataRepository,
    private val dataRankRepository: IDataRankRepository,
    private val dataSplineRepository: IDataSplineRepository,
    private val dataNoteRepository: IDataNoteRepository
) : IProjectRepository {
    override fun getAllProjects(): Flow<List<ProjectEntity>> =
        dataProjectRepository.getAllProjects()
    override fun getProjectRelation(id: Int): Flow<ProjectRelation> =
        dataProjectRepository.getProjectRelation(id)
    override suspend fun createProject() {
        val projectEntity = ProjectEntity(
            id = 0,
            thumbnailPath = null,
            title = "Project",
            description = "Description",
            author = "Author",
            startOffset = 0,
            duration = 0,
            bpm = 152,
            audioPath = null
        )
        val projectId = dataProjectRepository.insertProject(projectEntity).toInt()
        val boardEntity = BoardEntity(
            id = 0,
            projectId = projectId,
            origin = PointModel(0f, 0f),
            width = 1f,
            height = 1f
        )
        dataBoardRepository.insertBoard(boardEntity)
    }
    override suspend fun importProject(projectRelation: ProjectRelation): Int {
        val projectEntity = projectRelation.project.copy(id = 0)
        val projectEntityId = dataProjectRepository.insertProject(projectEntity).toInt()

        projectRelation.metaData.forEach {
            val metaDataEntity = it.copy(
                id = 0,
                projectId = projectEntityId
            )

            metaDataRepository.insertMetaData(metaDataEntity)
        }

        projectRelation.ranks.forEach {
            val rankEntity = it.copy(
                id = 0,
                projectId = projectEntityId
            )

            dataRankRepository.insertRank(rankEntity)
        }

        val boardEntity = projectRelation.board.board.copy(
            id = 0,
            projectId = projectEntityId
        )
        val boardEntityId = dataBoardRepository.insertBoard(boardEntity).toInt()

        projectRelation.board.splines.forEach { splineRelation ->
            val splineEntity = splineRelation.spline.copy(
                id = 0,
                boardId = boardEntityId
            )

            val splineEntityId = dataSplineRepository.insertSpline(splineEntity).toInt()

            val notes = splineRelation.notes.forEach { noteRelation ->
                val noteEntity = noteRelation.note.copy(
                    id = 0,
                    splineId = splineEntityId
                )

                // add note dao
                val noteEntityId = dataNoteRepository.insertNote(noteEntity).toInt()

                noteRelation.metaData.forEach {
                    val metaDataEntity = it.copy(
                        id = 0,
                        noteId = noteEntityId
                    )

                    metaDataRepository.insertMetaData(metaDataEntity)
                }
            }
        }

        return projectEntityId
    }
    override suspend fun duplicateProject(id: Int): Int {
        val projectRelation = dataProjectRepository.getProjectRelation(id).first()

        return importProject(projectRelation)
    }
    override suspend fun deleteProject(id: Int) {
        val projectEntity = dataProjectRepository.getProject(id).first()
        dataProjectRepository.deleteProject(projectEntity)
    }
}