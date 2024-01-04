package com.funnypaper.simme.domain.repository

import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.entity.TimingEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation
import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.data.repository.board.IDataBoardRepository
import com.funnypaper.simme.data.repository.metadata.IMetaDataRepository
import com.funnypaper.simme.data.repository.note.IDataNoteRepository
import com.funnypaper.simme.data.repository.project.IDataProjectRepository
import com.funnypaper.simme.data.repository.rank.IDataRankRepository
import com.funnypaper.simme.data.repository.spline.IDataSplineRepository
import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.domain.model.PointModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val dataProjectRepository: IDataProjectRepository,
    private val dataBoardRepository: IDataBoardRepository,
    private val metaDataRepository: IMetaDataRepository,
    private val dataRankRepository: IDataRankRepository,
    private val dataSplineRepository: IDataSplineRepository,
    private val dataNoteRepository: IDataNoteRepository,
    private val dataAudioRepository: IDataAudioRepository,
    private val dataTimingRepository: IDataTimingRepository,
) : IProjectRepository {

    // TODO: Split data management

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
        val timingEntity = TimingEntity(
            id = 0,
            projectId = projectId,
            bpm = 1,
            offset = 0,
            millis = 1
        )
        dataTimingRepository.insertTiming(timingEntity)
    }
    override suspend fun importProject(projectRelation: ProjectRelation): Int {
        val projectEntity = projectRelation.project.copy(id = 0)
        val projectEntityId = dataProjectRepository.insertProject(projectEntity).toInt()

        projectRelation.audio?.let {
            val audioEntity = it.copy(
                id = 0,
                projectId = projectEntityId
            )
            dataAudioRepository.insertAudio(audioEntity)
        }

        val timingEntity = projectRelation.timing.copy(
            id = 0,
            projectId = projectEntityId
        )
        dataTimingRepository.insertTiming(timingEntity)

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

        val boardEntity = projectRelation.board.space.copy(
            id = 0,
            projectId = projectEntityId
        )
        val boardEntityId = dataBoardRepository.insertBoard(boardEntity).toInt()

        projectRelation.board.notePaths.forEach { splineRelation ->
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