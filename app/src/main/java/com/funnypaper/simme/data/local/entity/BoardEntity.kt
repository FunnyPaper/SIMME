package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.funnypaper.simme.domain.model.PointModel

@Entity(
    tableName = "boards",
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
data class BoardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("project_id")
    val projectId: Int,
    val origin: PointModel,
    val width: Float,
    val height: Float
) {
    init {
        require(width > 0f)
        require(height > 0f)
    }
}
