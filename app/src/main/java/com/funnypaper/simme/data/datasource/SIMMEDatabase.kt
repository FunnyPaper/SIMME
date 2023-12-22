package com.funnypaper.simme.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class SIMMEDatabase : RoomDatabase() {
}