package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "timing",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["project_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("project_id")]
)
data class TimingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("project_id")
    val projectId: Int,
    val bpm: Int,
    val offset: Long,
    val millis: Long
) {
    init {
        require(bpm > 0)
        require(millis > 0)
    }
}
