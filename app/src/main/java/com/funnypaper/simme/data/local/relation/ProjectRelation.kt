package com.funnypaper.simme.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.funnypaper.simme.data.local.entity.AudioEntity
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.entity.RankEntity
import com.funnypaper.simme.data.local.entity.TimingEntity

data class ProjectRelation(
    @Embedded val project: ProjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id",
        entity = MetaDataEntity::class
    )
    val metaData: List<MetaDataEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id",
        entity = RankEntity::class
    )
    val ranks: List<RankEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id",
        entity = BoardEntity::class
    )
    val board: BoardRelation,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id",
        entity = AudioEntity::class
    )
    val audio: AudioEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id",
        entity = TimingEntity::class
    )
    val timing: TimingEntity
)