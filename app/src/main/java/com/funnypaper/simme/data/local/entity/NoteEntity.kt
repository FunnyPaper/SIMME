package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = SplineEntity::class,
            parentColumns = ["id"],
            childColumns = ["spline_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("spline_id")]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo("points_per_beat")
    val pointsPerBeat: Int,
    val length: Int,
    @ColumnInfo("start_time")
    val startTime: Int,
    @ColumnInfo("spline_id")
    val splineId: Int,
)
