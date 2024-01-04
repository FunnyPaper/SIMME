package com.funnypaper.simme.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.data.local.entity.NoteEntity

data class NoteRelation(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id",
        entity = MetaDataEntity::class
    )
    val metaData: List<MetaDataEntity>
)