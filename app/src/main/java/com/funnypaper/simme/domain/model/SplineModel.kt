package com.funnypaper.simme.domain.model

data class SplineModel(
    val points: MutableList<PointModel>
) {
    init {
        // Check for segments validity
        require(points.size % 4 == 0)
    }
    override fun toString(): String = points.joinToString(" ") { it.toString() }

    companion object {
        fun fromString(value: String) = value
            .split(" ")
            .mapTo(mutableListOf()) { PointModel.fromString(it) }
                .let { SplineModel(it) }
    }
}
