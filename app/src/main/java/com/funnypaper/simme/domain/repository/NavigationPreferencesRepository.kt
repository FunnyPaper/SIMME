package com.funnypaper.simme.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class NavigationPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) {
    val projectId: Flow<Int?> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[PROJECT_ID]
        }

    suspend fun saveProjectId(projectId: Int?) {
        dataStore.edit {
            try {
                if (projectId != null) {
                    it[PROJECT_ID] = projectId
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error saving preferences")
            }
        }
    }

    private companion object {
        val PROJECT_ID = intPreferencesKey("projectId")
        const val TAG = "NavigationPreferencesRepository"
    }
}