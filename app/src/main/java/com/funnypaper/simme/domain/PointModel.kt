package com.funnypaper.simme.domain

@JvmInline
value class PointModel(val data: MutableList<Float>) {
    init {
        require(data.size == 2)
    }
    var x: Float
        get() = data[0]
        set(value) {
            data[0] = value
        }

    var y: Float
        get() = data[1]
        set(value) {
            data[1] = value
        }
}
