package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "ranks",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["project_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("project_id")]
)
data class RankEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "project_id")
    val projectId: Int,
    val name: String,
    @ColumnInfo(name = "required_points")
    val requiredPoint: Int,
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String?
) {
    init {
        require(name.isNotBlank())
        require(requiredPoint > 0)
    }
}