package com.funnypaper.simme.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.entity.SplineEntity

data class BoardRelation(
    @Embedded val space: BoardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "board_id",
        entity = SplineEntity::class
    )
    val notePaths: List<SplineRelation>
)
