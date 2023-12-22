package com.funnypaper.simme.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class MetaDataEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val data: String
)
