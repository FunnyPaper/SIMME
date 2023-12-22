package com.funnypaper.simme.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ranks")
data class RankEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @ColumnInfo(name = "required_points")
    val requiredPoint: Int,
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String
)