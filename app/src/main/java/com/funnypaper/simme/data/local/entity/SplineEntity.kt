package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.funnypaper.simme.domain.model.SplineModel

@Entity(
    tableName = "splines",
    foreignKeys = [
        ForeignKey(
            entity = BoardEntity::class,
            parentColumns = ["id"],
            childColumns = ["board_id"]
        )
    ],
    indices = [Index("board_id")]
)
data class SplineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("board_id")
    val boardId: Int,
    val name: String,
    @ColumnInfo("beat_length")
    val beatLength: Int,
    val path: SplineModel,
)
