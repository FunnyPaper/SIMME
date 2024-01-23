package com.funnypaper.simme.ui.screens.globalproperties

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.funnypaper.simme.R
import com.funnypaper.simme.domain.extensions.MediaPlayerState
import com.funnypaper.simme.domain.extensions.formatMillis
import com.funnypaper.simme.domain.extensions.rememberMediaPlayer
import com.funnypaper.simme.ui.shared.audiovisualizer.AudioVisualizer
import com.funnypaper.simme.ui.shared.picker.UriPicker
import kotlinx.coroutines.launch

@Composable
fun AudioProperties(
    audioViewModel: AudioPropertiesViewModel = hiltViewModel<AudioPropertiesViewModel>(),
) {
    val audioState by audioViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (val audio = audioState) {
            is AudioFileUIState.None -> {
                AudioPropertiesSelectAudio {
                    coroutineScope.launch {
                        audioViewModel.updateAudioFile(it)
                    }
                }
            }

            is AudioFileUIState.Loading -> {
                AudioPropertiesAudioLoading()
            }

            is AudioFileUIState.Loaded -> {
                AudioPropertiesLoaded(audio = audio) {
                    coroutineScope.launch {
                        audioViewModel.deleteAudioFile()
                    }
                }
            }
        }
    }
}

@Composable
private fun AudioPropertiesSelectAudio(
    modifier: Modifier = Modifier,
    onAudioUriPick: (Uri) -> Unit = {},
) {
    UriPicker(
        onUriPickSuccess = {
            onAudioUriPick(it)
        },
        extensions = arrayOf("mp3"),
        modifier = modifier
    )
}

@Composable
private fun AudioPropertiesAudioLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.card_spacing)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator()
        Text(text = stringResource(id = R.string.loading_audio_tip))
    }
}

@Composable
private fun AudioPropertiesLoaded(
    audio: AudioFileUIState.Loaded,
    modifier: Modifier = Modifier,
    onAudioRemove: () -> Unit = {},
) {
    val mediaPlayerState: MediaPlayerState = rememberMediaPlayer(uri = audio.uri)

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.card_spacing)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = audio.millis.formatMillis())

        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.card_spacing)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AudioVisualizer(
                data = audio.pcm,
                progression = mediaPlayerState.progression,
                paused = !mediaPlayerState.isPlaying,
                onHorizontalDragStart = {
                    mediaPlayerState.startDrag(it)
                },
                onHorizontalDragEnd = {
                    mediaPlayerState.dragEnd()
                },
                onHorizontalDrag = { dragAmount ->
                    mediaPlayerState.drag(dragAmount)
                },
                modifier = Modifier.weight(1f)
            )

            if (mediaPlayerState.isPlaying) {
                OutlinedIconButton(onClick = mediaPlayerState::pause) {
                    Icon(imageVector = Icons.Filled.Pause, contentDescription = null)
                }
            } else {
                OutlinedIconButton(onClick = mediaPlayerState::resume) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
                }
            }

            OutlinedIconButton(onClick = onAudioRemove) {
                Icon(imageVector = Icons.Filled.DeleteForever, contentDescription = null)
            }
        }

        Text(text = audio.name)
    }
}