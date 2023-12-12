package com.funnypaper.simme.ui.shared.extensions

import android.content.res.Resources
import android.media.MediaExtractor
import android.media.MediaFormat

fun MediaExtractor.getAudioTrackIndex() = (0..trackCount).find {
    val mime = getTrackFormat(it).getString(MediaFormat.KEY_MIME)
    mime?.startsWith("audio/") ?: false
} ?: throw Resources.NotFoundException()