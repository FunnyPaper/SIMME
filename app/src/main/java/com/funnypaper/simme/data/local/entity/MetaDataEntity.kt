package com.funnypaper.simme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "meta_data",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["project_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["note_id"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("project_id"),
        Index("note_id"),
    ]
)
data class MetaDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "project_id")
    val projectId: Int?,
    @ColumnInfo(name = "note_id")
    val noteId: Int?,
    val data: String,
) {
    init {
        require(data.isNotBlank())
    }
}
