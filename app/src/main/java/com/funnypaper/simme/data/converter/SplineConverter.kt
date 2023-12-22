package com.funnypaper.simme.data.converter

import androidx.room.TypeConverter
import com.funnypaper.simme.domain.CurveModel
import com.funnypaper.simme.domain.PointModel
import com.funnypaper.simme.domain.SplineModel

class SplineConverter {
    @TypeConverter
    fun to(value: SplineModel): String =
        value.segments.map { segment ->
            segment.points.joinToString(" ") { point ->
                point.data.joinToString(",") { it.toString() }
            }
        }.joinToString("|")

    @TypeConverter
    fun from(value: String): SplineModel =
        SplineModel(
            value.split("|").mapTo(mutableListOf()) { segment ->
                CurveModel(
                    segment.split(" ").map { point ->
                        PointModel(point.split(",").mapTo(mutableListOf()) { it.toFloat() })
                    }
                )
            }
        )
}