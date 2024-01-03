package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String?,
    val title: String,
    val description: String,
    val author: String,
    @ColumnInfo(name = "start_offset")
    val startOffset: Long,
    val duration: Long,
    val bpm: Int,
    @ColumnInfo(name = "audio_path")
    val audioPath: String?,
) {
    init {
        require(title.isNotBlank())
        require(bpm > 0)
    }
}
