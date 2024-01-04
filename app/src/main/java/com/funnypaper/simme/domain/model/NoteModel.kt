package com.funnypaper.simme.domain.model

import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.data.local.relation.NoteRelation

data class NoteModel(
    val name: String,
    val pointsPerBeat: Int,
    val length: Int,
    val startTime: Int,
    val metaData: MutableList<MetaDataModel>
) {
    var id: Int = 0
}

fun NoteRelation.toNoteModel() =
    NoteModel(
        name = note.name,
        pointsPerBeat = note.pointsPerBeat,
        length = note.length,
        startTime = note.startTime,
        metaData = metaData.mapTo(mutableListOf(), MetaDataEntity::toMetaDataModel)
    ).apply { id = note.id }