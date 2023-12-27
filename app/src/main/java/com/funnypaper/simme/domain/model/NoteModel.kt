package com.funnypaper.simme.domain.model

data class NoteModel(
    val name: String,
    val pointsPerBeat: Int,
    val length: Int,
    val startTime: Int,
)
