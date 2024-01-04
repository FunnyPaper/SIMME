package com.funnypaper.simme.domain.model

data class PointModel(
    val x: Float,
    val y: Float
) {
    override fun toString(): String = "$x,$y"

    companion object {
        fun fromString(value: String) = value
            .split(",")
            .let { PointModel(it[0].toFloat(), it[1].toFloat()) }
    }
}
