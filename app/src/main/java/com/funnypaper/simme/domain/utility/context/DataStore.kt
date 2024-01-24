package com.funnypaper.simme.domain.utility.context

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val NAVIGATION_DATA_STORE_NAME = "navigation"
val Context.navigationDataStore: DataStore<Preferences> by preferencesDataStore(
    name = NAVIGATION_DATA_STORE_NAME
)