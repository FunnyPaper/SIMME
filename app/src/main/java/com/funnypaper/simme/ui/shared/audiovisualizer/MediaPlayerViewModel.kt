package com.funnypaper.simme.ui.shared.audiovisualizer

import android.content.Context
import android.media.MediaExtractor
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnypaper.simme.domain.extensions.progression
import com.funnypaper.simme.domain.extensions.setUri
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private var refreshPlayerJob: Job? = null
    private var pcmJob: Job? = null

    private val mediaPlayer = MediaPlayer()
    private var isMediaPlayerPrepared = false

    init {
        mediaPlayer.setOnPreparedListener {
            isMediaPlayerPrepared = true
        }
    }

    private val _mediaPlayerUIState = MutableMediaPlayerUIState()
    val mediaPlayerUIState: MediaPlayerUIState = _mediaPlayerUIState
    fun setUri(uri: Uri?) = uri?.let {
        isMediaPlayerPrepared = false
        mediaPlayer.setUri(context, uri)
        _mediaPlayerUIState.uri = uri

        pcmJob?.cancel()
        pcmJob = getPcmJob(uri)
    }

    fun updateProgression(progression: Float) {
        _mediaPlayerUIState.progression = progression
        if (isMediaPlayerPrepared) {
            mediaPlayer.seekTo((progression * mediaPlayer.duration).toInt())
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying && isMediaPlayerPrepared) {
            mediaPlayer.pause()
            _mediaPlayerUIState.isPlaying = false

            refreshPlayerJob?.cancel()
        }
    }

    fun resume() {
        if (!mediaPlayer.isPlaying && isMediaPlayerPrepared) {
            mediaPlayer.start()
            _mediaPlayerUIState.isPlaying = true

            refreshPlayerJob?.cancel()
            refreshPlayerJob = getRefreshPlayerJob()
        }
    }

    private fun getRefreshPlayerJob() = viewModelScope.launch {
        while (true) {
            if (!_mediaPlayerUIState.isPlaying) {
                return@launch
            }

            _mediaPlayerUIState.progression = mediaPlayer.progression
            delay(200)
        }
    }
    private fun getPcmJob(uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        _mediaPlayerUIState.isPcmLoading = true
        val mediaExtractor = MediaExtractor()

        mediaExtractor.apply {
            setDataSource(context, uri, emptyMap())
        }

        getPCMData(mediaExtractor).collect {
            Snapshot.withMutableSnapshot {
                _mediaPlayerUIState.pcm = it
                _mediaPlayerUIState.isPcmLoading = false
            }
        }

        mediaExtractor.release()
    }
}

@Stable
interface MediaPlayerUIState {
    val uri: Uri?
    val progression: Float
    val isPlaying: Boolean
    val pcm: List<Float>
    val isPcmLoading: Boolean
}

private class MutableMediaPlayerUIState : MediaPlayerUIState {
    override var uri: Uri? by mutableStateOf(null)
    override var progression: Float by mutableFloatStateOf(0f)
    override var isPlaying: Boolean by mutableStateOf(false)
    override var pcm: List<Float> by mutableStateOf(emptyList())
    override var isPcmLoading: Boolean by mutableStateOf(false)
}