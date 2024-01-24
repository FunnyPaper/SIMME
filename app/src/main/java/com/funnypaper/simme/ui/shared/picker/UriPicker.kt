package com.funnypaper.simme.ui.shared.picker

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.theme.SIMMETheme
import com.funnypaper.simme.ui.theme.jura

@Composable
fun UriPicker(
    modifier: Modifier = Modifier,
    onUriPickSuccess: (Uri) -> Unit = {},
    onUriPickInvalid: (Uri) -> Unit = {},
    onUriPickUnknown: (Uri) -> Unit = {},
    onUriPickInterrupted: () -> Unit = {},
    extensions: Array<String> = emptyArray(),
    shape: Shape = RoundedCornerShape(16.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    thickness: Dp = 2.dp,
    padding: Dp = 16.dp,
) {
    val context = LocalContext.current
    val mimeTypes = remember(extensions) {
        // Clip everything before slash (inclusive)
        // ex. application/json -> json
        // audio/mpeg -> mpeg
        val mimeTypeMap = MimeTypeMap.getSingleton()
        extensions.mapNotNull { mimeTypeMap.getMimeTypeFromExtension(it) }
            .distinct()
            .toTypedArray()
    }
    val launcher = rememberUriPickerLauncher {
        if (it != null) {
            var extension = MimeTypeMap.getFileExtensionFromUrl(it.toString())

            // Sometimes Uri does not contain information about extension and name
            if (extension.isEmpty()) {
                val mimeType = context.contentResolver.getType(it)
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            }

            // Built in file browser prevents user from clicking invalid files (respects set boundaries of mime types)
            // Which is not the case with custom file browsers / managers
            if (extension in extensions) {
                onUriPickSuccess(it)
            } else if (extension.isNotEmpty()) {
                onUriPickInvalid(it)
            } else {
                // Uri have extension that is not commonly-known MIME type
                onUriPickUnknown(it)
            }
        } else {
            onUriPickInterrupted()
        }
    }

    CompositionLocalProvider(
        LocalContentColor provides color,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(padding * .5f),
            modifier = modifier
                .clickable {
                    launcher.launch(mimeTypes)
                }
                .border(
                    width = thickness,
                    color = color,
                    shape = shape
                )
                .widthIn(128.dp)
                .width(IntrinsicSize.Max)
                .padding(padding)
        ) {
            Text(
                text = stringResource(id = R.string.uri_picker_label),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = jura,
                    fontWeight = FontWeight.Bold
                )
            )
            Divider(
                color = color,
                modifier = Modifier.fillMaxWidth(.9f)
            )
            Text(
                text = extensions.joinToString { "*.$it" },
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = jura,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

/**
 * Shortcut for ActivityResultContracts.OpenDocument() launcher.
 */
@Composable
fun rememberUriPickerLauncher(onUriPick: (Uri?) -> Unit) =
    rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument(), onUriPick)

@Preview
@Composable
private fun UriPickerPreview() {
    SIMMETheme {
        Surface {
            var uri by rememberSaveable(
                saver = Saver(
                    save = { it.value.toString() },
                    restore = { mutableStateOf(Uri.parse(it)) }
                )
            ) {
                mutableStateOf(Uri.EMPTY)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                UriPicker(
                    onUriPickSuccess = { uri = it },
                    extensions = arrayOf(
                        "json",
                        "mp3",
                        "csv"
                    ),
                )

                Text(text = uri.toString())
            }
        }
    }
}