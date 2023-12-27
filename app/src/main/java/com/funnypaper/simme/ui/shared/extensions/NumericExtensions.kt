package com.funnypaper.simme.ui.shared.extensions

fun Float.scaleNumber(oldMin: Float, oldMax: Float, newMin: Float, newMax: Float)
        = ((this - oldMin) / (oldMax - oldMin)) * (newMax - newMin) + newMin

fun Long.formatMillis(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60

    val ms = "${this % 1000}".padStart(3, '0')
    val s = "${seconds % 60}".padStart(2, '0')
    val min = "${minutes % 60}".padStart(2, '0')
    val h = hours.toString().padStart(2, '0')

    return "$h:$min:$s.$ms"
}