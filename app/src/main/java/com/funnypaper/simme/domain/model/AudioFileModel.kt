package com.funnypaper.simme.domain.model

import android.net.Uri

data class AudioFileModel(
    val audioUri: Uri,
    val pcm: List<Float>,
    val millis: Long
)
