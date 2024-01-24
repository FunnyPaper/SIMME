package com.funnypaper.simme.domain.model

import android.net.Uri
import com.funnypaper.simme.data.local.entity.AudioEntity

data class AudioFileModel(
    val name: String,
    val uri: Uri,
    val pcm: List<Float> = emptyList(),
    val millis: Long,
) {
    var id: Int = 0
}

fun AudioEntity.toAudioFileModel() =
    AudioFileModel(
        name = name,
        uri = uriPath?.let { Uri.parse(it) } ?: Uri.EMPTY,
        millis = millis
    ).apply { id = this@toAudioFileModel.id }