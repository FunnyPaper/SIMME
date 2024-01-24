package com.funnypaper.simme.domain.extensions

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.funnypaper.simme.domain.utility.audio.getPCMData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun MediaPlayer.setUri(context: Context, uri: Uri) = apply {
    reset()
    setDataSource(context, uri)
    prepare()
}

val MediaPlayer.progression
    get() = let {
        val res = currentPosition.toFloat() / duration.toFloat()
        if(res.isNaN()) 0f else res
    }

@Composable
fun rememberMediaPlayer(
    uri: Uri?,
): MediaPlayerState {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Initialize media player
    val mediaPlayer = rememberSaveable(uri, saver = MediaPlayerState.Saver(context, scope)) {
        MediaPlayerState(context, scope)
    }

    // Restart and feed media player with new uri data
    DisposableEffect(uri) {
        mediaPlayer.setUri(uri)
        onDispose {
            mediaPlayer.restart()
            mediaPlayer.release()
        }
    }

    return mediaPlayer
}

@Composable
fun rememberPCMMediaState(uri: Uri): List<Float> {
    val context = LocalContext.current
    var saved by rememberSaveable(context, uri, saver = Saver(
        save = { it.value.joinToString(",") },
        restore = { mutableStateOf(it.split(",").map { it.toFloat() }) }
    )) {
        mutableStateOf(emptyList<Float>())
    }

    LaunchedEffect(context, uri) {
        if (saved.isEmpty()) {
            saved = getPCMData(context, uri)
        }
    }

    return saved
}

@Stable
class MediaPlayerState internal constructor(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) {
    private val mediaPlayer = MediaPlayer()
    private var playAfterDrag = false
    private var refreshPlayerJob: Job? = null
    private var isMediaPlayerPrepared = false

    init {
        mediaPlayer.setOnPreparedListener {
            isMediaPlayerPrepared = true
        }

        mediaPlayer.setOnCompletionListener {
            isPlaying = false
            refreshPlayerJob?.cancel()
        }
    }

    var progression: Float by mutableFloatStateOf(0f)
        private set

    var isPlaying: Boolean by mutableStateOf(false)
        private set

    internal fun setUri(uri: Uri?) = uri?.let {
        isMediaPlayerPrepared = false
        mediaPlayer.setUri(context, uri)
        refreshPlayerJob?.cancel()
    }

    fun startDrag(drag: Float) {
        if (isMediaPlayerPrepared) {
            playAfterDrag = isPlaying
            pause()
            updateProgression(drag.coerceIn(0f, 1f))
        }
    }

    fun dragEnd() {
        if (playAfterDrag && isMediaPlayerPrepared) {
            resume()
        }
    }

    fun drag(drag: Float) = updateProgression(
        progression + drag.coerceIn(
            -progression, 1 - progression
        )
    )

    private fun updateProgression(progression: Float) {
        if (isMediaPlayerPrepared) {
            mediaPlayer.seekTo((progression * mediaPlayer.duration).toInt())
        }
        this.progression = progression
    }

    fun pause() {
        if (mediaPlayer.isPlaying && isMediaPlayerPrepared) {
            mediaPlayer.pause()
            isPlaying = false
            refreshPlayerJob?.cancel()
        }
    }

    fun resume() {
        if (!mediaPlayer.isPlaying && isMediaPlayerPrepared) {
            mediaPlayer.start()
            isPlaying = true
            refreshPlayerJob?.cancel()
            refreshPlayerJob = getRefreshPlayerJob()
        }
    }

    private fun getRefreshPlayerJob() = coroutineScope.launch {
        while (true) {
            progression = mediaPlayer.progression
            delay(200)
        }
    }

    internal fun restart() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
        progression = 0f
        isPlaying = false
    }

    internal fun release() {
        refreshPlayerJob?.cancel()
        mediaPlayer.release()
    }

    companion object {
        private const val PROGRESSION_KEY = "progression"
        private const val IS_PLAYING_KEY = "isPlaying"
        fun Saver(context: Context, scope: CoroutineScope) = mapSaver(
            save = {
                mapOf(
                    PROGRESSION_KEY to it.progression,
                    IS_PLAYING_KEY to it.isPlaying
                )
            },
            restore = {
                MediaPlayerState(context, scope).apply {
                    progression = it[PROGRESSION_KEY] as Float
                    isPlaying = it[IS_PLAYING_KEY] as Boolean
                }
            }
        )
    }
}
