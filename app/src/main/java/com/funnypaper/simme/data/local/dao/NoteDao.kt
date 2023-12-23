package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.relation.NoteRelation

@Dao
interface NoteDao : ICRUDDao<NoteEntity> {
    @Query("SELECT * FROM notes WHERE id = :value")
    suspend fun getNoteById(value: Int): NoteEntity

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :value")
    suspend fun getNoteRelationById(value: Int): NoteRelation

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>
}