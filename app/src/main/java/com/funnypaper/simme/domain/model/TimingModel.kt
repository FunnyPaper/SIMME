package com.funnypaper.simme.domain.model

import com.funnypaper.simme.data.local.entity.TimingEntity

data class TimingModel(
    val bpm: Int,
    val offset: Long,
    val millis: Long
)  {
    var id: Int = 0
    val duration get() = millis - offset
}

fun TimingEntity.toTimingModel() =
    TimingModel(
        bpm = bpm,
        offset = offset,
        millis = millis
    ).apply { id = this@toTimingModel.id }

fun TimingModel.toTimingEntity(projectId: Int) =
    TimingEntity(
        id = id,
        bpm = bpm,
        offset = offset,
        millis = millis,
        projectId = projectId
    )