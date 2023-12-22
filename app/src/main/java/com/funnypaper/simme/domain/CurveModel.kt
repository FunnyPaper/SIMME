package com.funnypaper.simme.domain

@JvmInline
value class CurveModel(val points: List<PointModel>) {
    init {
        require(points.size == 4)
    }

    val start get() = points[0]
    val end get() = points[3]
}