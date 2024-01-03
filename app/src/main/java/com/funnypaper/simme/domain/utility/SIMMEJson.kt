package com.funnypaper.simme.domain.utility

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object SIMMEJson {
    inline fun <reified T>parse(jsonString: String): T =
        Gson().fromJson(jsonString, T::class.java)

    fun toString(obj: Any?): String =
        GsonBuilder().setPrettyPrinting().create().toJson(obj)

    fun <T>writeToFile(context: Context, directoryUri: Uri, obj: T) {
        context.contentResolver.openOutputStream(directoryUri)?.use {
            it.write(toString(obj).toByteArray())
        }
    }

    inline fun <reified T>readFromFile(context: Context, pathUri: Uri): T? =
        context.contentResolver.openInputStream(pathUri)?.use {
            parse<T>(it.readBytes().decodeToString())
        }
}
