package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.funnypaper.simme.domain.PointModel
import java.util.UUID

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
)
