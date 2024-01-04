package com.funnypaper.simme.data.local.converter

import androidx.room.TypeConverter
import com.funnypaper.simme.domain.model.SplineModel

class SplineConverter {
    @TypeConverter
    fun toSplineModel(value: SplineModel): String = value.toString()

    @TypeConverter
    fun fromString(value: String): SplineModel = SplineModel.fromString(value)
}