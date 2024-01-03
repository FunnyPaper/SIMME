package com.funnypaper.simme.ui.screens.projectlist

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnypaper.simme.data.local.relation.ProjectRelation
import com.funnypaper.simme.domain.model.AudioFileModel
import com.funnypaper.simme.domain.model.BoardModel
import com.funnypaper.simme.domain.model.MetaDataModel
import com.funnypaper.simme.domain.model.RankModel
import com.funnypaper.simme.domain.model.TimingModel
import com.funnypaper.simme.domain.repository.IProjectRepository
import com.funnypaper.simme.domain.utility.SIMMEJson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val projectRepository: IProjectRepository,
) : ViewModel() {
    private val _detailsUIState: MutableStateFlow<ProjectItemDetailsUIState?> =
        MutableStateFlow(null)
    val detailsUIState = _detailsUIState.asStateFlow()

    val listUIState = projectRepository.getAllProjects().map {
        it.map { projectEntity ->
            ProjectItemUIState(
                id = projectEntity.id,
                thumbnailUri = Uri.EMPTY,
                title = projectEntity.title,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun createProject() = viewModelScope.launch {
        projectRepository.createProject()
    }

    fun importProject(uri: Uri) {
        SIMMEJson.readFromFile<ProjectRelation>(context, uri)?.let {
            viewModelScope.launch {
                val id = projectRepository.importProject(it)
                previewProject(id)
            }
        }
    }

    fun exportProject(id: Int, uri: Uri) {
        viewModelScope.launch {
            val relation = projectRepository.getProjectRelation(id).first()
            SIMMEJson.writeToFile(context, uri, relation)
        }
    }

    fun duplicateProject(id: Int) = viewModelScope.launch {
        val duplicatedProjectId = projectRepository.duplicateProject(id)
        previewProject(duplicatedProjectId)
    }

    fun deleteProject(id: Int) = viewModelScope.launch {
        projectRepository.deleteProject(id)
        _detailsUIState.value = null
    }

    fun previewProject(id: Int?) = viewModelScope.launch {
        _detailsUIState.value = id?.let {
            mapRelationToUIState(it).first()
        }
    }

    private fun mapRelationToUIState(id: Int): Flow<ProjectItemDetailsUIState> {
        return projectRepository.getProjectRelation(id).map { relation ->
            ProjectItemDetailsUIState(
                id = relation.project.id,
                thumbnailUri = Uri.EMPTY,
                title = relation.project.title,
                description = relation.project.description,
                author = relation.project.author,
                timing = TimingModel(
                    bpm = relation.project.bpm,
                    millis = relation.project.duration,
                    offset = relation.project.startOffset
                ),
                audio = null,
                board = BoardModel(
                    relation.board.board.origin,
                    relation.board.board.width,
                    relation.board.board.height
                ),
                metaData = relation.metaData.map { MetaDataModel(it.data) },
                ranks = relation.ranks.map {
                    RankModel(
                        it.name,
                        it.requiredPoint,
                        Uri.EMPTY
                    )
                }
            )
        }
    }
}

data class ProjectItemDetailsUIState(
    val id: Int,
    val thumbnailUri: Uri,
    val title: String,
    val description: String,
    val author: String,
    val timing: TimingModel,
    val audio: AudioFileModel?,
    val board: BoardModel,
    val metaData: List<MetaDataModel>,
    val ranks: List<RankModel>,
)

data class ProjectItemUIState(
    val id: Int,
    val thumbnailUri: Uri,
    val title: String,
)