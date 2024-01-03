package com.funnypaper.simme.domain.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

fun Uri.getFilename(contentResolver: ContentResolver): String? {
    if(this.scheme == "content") {
        val cursor = contentResolver.query(this, null, null, null)
        cursor?.use {
            if(it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                return it.getString(index)
            }
        }
        cursor?.close()
    }

    return null
}

fun Int.resourceIdToUri(context: Context): Uri =
    with(context.resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(this@resourceIdToUri))
            .appendPath(getResourceTypeName(this@resourceIdToUri))
            .appendPath(getResourceEntryName(this@resourceIdToUri))
            .build()
    }

@Composable
fun rememberResourceUri(resourceId: Int): Uri {
    val context = LocalContext.current

    return remember(resourceId) {
        resourceId.resourceIdToUri(context)
    }
}