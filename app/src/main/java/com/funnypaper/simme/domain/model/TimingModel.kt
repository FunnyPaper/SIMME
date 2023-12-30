package com.funnypaper.simme.domain.model

data class TimingModel(
    val bpm: Int,
    val offset: Long,
    val millis: Long
)  {
    val duration get() = millis - offset
}
