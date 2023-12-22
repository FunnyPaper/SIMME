package com.funnypaper.simme.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "boards")
data class BoardEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val width: Float,
    val height: Float
)
