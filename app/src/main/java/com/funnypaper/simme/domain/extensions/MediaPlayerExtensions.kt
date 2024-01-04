package com.funnypaper.simme.domain.extensions

import android.content.Context
import android.media.MediaExtractor
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.funnypaper.simme.domain.utility.audio.getPCMData
import kotlinx.coroutines.flow.single

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
    uri: Uri
): MediaPlayer {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // Initialize media player
    val mediaPlayer = remember {
        MediaPlayer()
    }

    // Restart and feed media player with new uri data
    DisposableEffect(uri) {
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepare()

        onDispose {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    // Clean media player after Application destroy
    DisposableEffect(mediaPlayer) {
        val observer = LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_DESTROY) {
                mediaPlayer.release()
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return mediaPlayer
}

@Composable
fun rememberPCMMediaState(uri: Uri): State<List<Float>> {
    val context = LocalContext.current
    return produceState(emptyList()) {
        val mediaExtractor = MediaExtractor().apply {
            setDataSource(context, uri, emptyMap())
        }

        value = getPCMData(mediaExtractor).single()
        mediaExtractor.release()
    }
}