package com.funnypaper.simme.domain.model

import com.funnypaper.simme.data.local.relation.BoardRelation
import com.funnypaper.simme.data.local.relation.SplineRelation

data class BoardModel(
    val origin: PointModel,
    val width: Float,
    val height: Float,
    val paths: List<NotePathModel>
) {
    var id: Int = 0
}

fun BoardRelation.toBoardModel() =
    BoardModel(
        origin = space.origin,
        width = space.width,
        height = space.height,
        paths = notePaths.map(SplineRelation::toNotePathModel)
    ).apply { id = space.id }