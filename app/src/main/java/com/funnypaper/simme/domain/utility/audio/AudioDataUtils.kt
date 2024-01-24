package com.funnypaper.simme.domain.utility.audio

import android.content.Context
import android.media.MediaCodec
import android.media.MediaCodec.BufferInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import com.funnypaper.simme.domain.extensions.getAudioTrackIndex
import kotlin.math.sqrt

fun shrinkArray(array: List<Float>, binCount: Int): List<Float> {
    return try {
        val newArray = mutableListOf<Float>()
        val n = (array.size / binCount.toFloat()).toInt()
        for (i in n..<array.size step n) {
            val sum = array.slice((i - n)..<i).sumOf {
                it.toLong().let { it * it }
            }
            val mean = sum / n.toDouble()
            newArray.add(sqrt(mean).toFloat())
        }
        newArray
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

fun getPCMData(context: Context, uri: Uri): List<Float> {
    val mediaExtractor = MediaExtractor().apply {
        setDataSource(context, uri, emptyMap())
    }

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

    try {
        val inputThreads = Thread { processInputBuffer(mediaCodec, mediaExtractor) }
            .apply { start() }

        val outputThreads = Thread { processOutputBuffer(mediaCodec, bufferInfo, pcm) }
            .apply { start() }

        inputThreads.join()
        outputThreads.join()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // Release resources
    mediaCodec.stop()
    mediaCodec.release()
    mediaExtractor.release()

    return pcm
}

private fun processInputBuffer(codec: MediaCodec, extractor: MediaExtractor) {
    while (true) {
        // Request next input buffer
        val inputBufferIndex = codec.dequeueInputBuffer(-1)
        if (inputBufferIndex >= 0) {
            // Get input buffer and feed it with MediaExtractor data
            val inputBuffer = codec.getInputBuffer(inputBufferIndex)!!
            val sampleSize = synchronized(extractor) {
                extractor.readSampleData(inputBuffer, 0)
            }

            if (sampleSize >= 0) {
                codec.queueInputBuffer(inputBufferIndex, 0, sampleSize, extractor.sampleTime, 0)
                extractor.advance()
            } else {
                codec.queueInputBuffer(
                    inputBufferIndex,
                    0,
                    0,
                    0,
                    MediaCodec.BUFFER_FLAG_END_OF_STREAM
                )
                break
            }
        }
    }
}

private fun processOutputBuffer(codec: MediaCodec, bufferInfo: BufferInfo, pcm: MutableList<Float>) {
    while (true) {
        // Request next output buffer
        val outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, -1)
        if (outputBufferIndex >= 0) {
            // Get output buffer and write it's content to byte array
            val outputBuffer = codec.getOutputBuffer(outputBufferIndex)!!
            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                // End of stream
                break
            } else {
                val audioData = ByteArray(bufferInfo.size)
                outputBuffer.get(audioData)

                // Average processed sample
                val sum = audioData.sum().toFloat()
                pcm.add((sum / audioData.size))
            }

            codec.releaseOutputBuffer(outputBufferIndex, false)
        }
    }
}