package com.funnypaper.simme.domain.model

import com.funnypaper.simme.data.local.relation.NoteRelation
import com.funnypaper.simme.data.local.relation.SplineRelation

data class NotePathModel(
    val name: String,
    val beatLength: Float,
    val spline: SplineModel,
    val notes: MutableList<NoteModel>
) {
    var id: Int = 0
}

fun SplineRelation.toNotePathModel() =
    NotePathModel(
        name = spline.name,
        beatLength = spline.beatLength,
        spline = spline.path,
        notes = notes.mapTo(mutableListOf(), NoteRelation::toNoteModel)
    ).apply { id = this@toNotePathModel.spline.id }