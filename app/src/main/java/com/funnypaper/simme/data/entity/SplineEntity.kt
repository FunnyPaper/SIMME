package com.funnypaper.simme.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.funnypaper.simme.data.converter.SplineConverter
import com.funnypaper.simme.domain.SplineModel
import java.util.UUID

@Entity
@TypeConverters(SplineConverter::class)
data class SplineEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val segments: SplineModel
)
