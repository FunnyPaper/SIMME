package com.funnypaper.simme.domain.model

import android.net.Uri
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.entity.RankEntity
import com.funnypaper.simme.data.local.relation.ProjectRelation

data class ProjectModel(
    val thumbnailUri: Uri,
    val title: String,
    val description: String,
    val author: String,
    val ranks: MutableList<RankModel>,
    val audioFile: AudioFileModel?,
    val timing: TimingModel,
    val board: BoardModel,
    val metaData: MutableList<MetaDataModel>
) {
    var id: Int = 0
}

fun ProjectRelation.toProjectModel() =
    ProjectModel(
        thumbnailUri = project.thumbnailPath?.let { Uri.parse(it) } ?: Uri.EMPTY,
        title = project.title,
        description = project.description,
        author = project.author,
        ranks = ranks.mapTo(mutableListOf(), RankEntity::toRankModel),
        audioFile = audio?.toAudioFileModel(),
        timing = timing.toTimingModel(),
        board = board.toBoardModel(),
        metaData = metaData.mapTo(mutableListOf(), MetaDataEntity::toMetaDataModel)
    ).apply { id = this@toProjectModel.project.id }
