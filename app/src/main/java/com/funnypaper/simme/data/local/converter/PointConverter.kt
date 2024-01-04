package com.funnypaper.simme.data.local.converter

import androidx.room.TypeConverter
import com.funnypaper.simme.domain.model.PointModel

class PointConverter {
    @TypeConverter
    fun toPointModel(value: PointModel): String = value.toString()

    @TypeConverter
    fun fromString(value: String): PointModel = PointModel.fromString(value)
}