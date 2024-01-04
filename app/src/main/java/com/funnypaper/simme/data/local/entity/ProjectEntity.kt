package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String?,
    val title: String,
    val description: String,
    val author: String,
) {
    init {
        require(title.isNotBlank())
    }
}
