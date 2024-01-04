package com.funnypaper.simme.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.entity.SplineEntity

data class SplineRelation(
    @Embedded val spline: SplineEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "spline_id",
        entity = NoteEntity::class
    )
    val notes: List<NoteRelation>
)