package com.funnypaper.simme.domain.model

import android.net.Uri

data class AudioFileModel(
    val name: String,
    val uri: Uri,
    val pcm: List<Float>,
    val millis: Long
)
