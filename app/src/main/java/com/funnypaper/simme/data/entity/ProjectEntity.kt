package com.funnypaper.simme.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String,
    val title: String,
    val description: String,
    val author: String,
    @ColumnInfo(name = "start_offset")
    val startOffset: Int,
    val bmp: Int,
    @ColumnInfo(name = "audio_path")
    val audioPath: Uri,
    val board: BoardEntity,
    val ranks: List<RankEntity>,
    @ColumnInfo(name = "meta_data")
    val metaData: List<MetaDataEntity>
)
