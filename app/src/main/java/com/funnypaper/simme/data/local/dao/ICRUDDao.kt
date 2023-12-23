package com.funnypaper.simme.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface ICRUDDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: T)

    @Delete
    suspend fun delete(value: T)

    @Update
    suspend fun update(value: T)
}