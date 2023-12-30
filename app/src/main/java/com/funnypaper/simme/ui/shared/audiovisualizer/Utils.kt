package com.funnypaper.simme.ui.shared.audiovisualizer

import android.media.MediaCodec
import android.media.MediaCodec.BufferInfo
import android.media.MediaCodecList
import android.media.MediaExtractor
import android.media.MediaFormat
import com.funnypaper.simme.domain.extensions.getAudioTrackIndex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.sqrt

fun shrinkArray(array: List<Float>, binCount: Int): List<Float> {
    val newArray = mutableListOf<Float>()
    val n = (array.size / binCount.toFloat()).toInt()

    for(i in 0..<binCount) {
        val sum = array.slice((i * n)..<((i + 1) * n)).sumOf {
            it.toLong().let { it * it }
        }
        val mean = sum / n.toDouble()
        newArray.add(sqrt(mean).toFloat())
    }

    return newArray
}

suspend fun getPCMData(mediaExtractor: MediaExtractor): Flow<List<Float>> = flow {
    // Select audio track
    val audioTrackIndex = mediaExtractor.getAudioTrackIndex()
    mediaExtractor.selectTrack(audioTrackIndex)

    // Retrieve proper decoder and configure it
    val mediaFormat = mediaExtractor.getTrackFormat(audioTrackIndex)
    val mime = mediaFormat.getString(MediaFormat.KEY_MIME)!!
    val mediaCodec = MediaCodec.createDecoderByType(mime)
    mediaCodec.configure(mediaFormat, null, null, 0)
    mediaCodec.start()

    // Process audio file content
    val pcm = mutableListOf<Float>()
    val bufferInfo = BufferInfo()
    while(!processInputBuffer(mediaCodec, mediaExtractor)) {
        processOutputBuffer(mediaCodec, bufferInfo, pcm)
    }

    // Release resources
    mediaCodec.stop()
    mediaCodec.release()

    emit(pcm)
}

private fun processInputBuffer(codec: MediaCodec, extractor: MediaExtractor): Boolean {
    // Request next input buffer
    val inputBufferIndex = codec.dequeueInputBuffer(-1)
    if(inputBufferIndex >= 0) {
        // Get input buffer and feed it with MediaExtractor data
        val inputBuffer = codec.getInputBuffer(inputBufferIndex)!!
        val sampleSize = extractor.readSampleData(inputBuffer, 0)

        if(sampleSize >= 0) {
            codec.queueInputBuffer(inputBufferIndex, 0, sampleSize, extractor.sampleTime, 0)
            extractor.advance()
        } else {
            codec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
            return true
        }
    }

    return false
}

private fun processOutputBuffer(codec: MediaCodec, bufferInfo: BufferInfo, pcm: MutableList<Float>) {
    // Request next output buffer
    val outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, -1)
    if (outputBufferIndex >= 0) {
        // Get output buffer and write it's content to byte array
        val outputBuffer = codec.getOutputBuffer(outputBufferIndex)!!
        val audioData = ByteArray(bufferInfo.size)
        outputBuffer.get(audioData)

        codec.releaseOutputBuffer(outputBufferIndex, false)

        // Average processed sample
        val sum = audioData.sum().toFloat()
        pcm.add((sum / audioData.size))
    }
}