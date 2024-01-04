package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.relation.NoteRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao : ICRUDDao<NoteEntity> {
    @Query("SELECT * FROM note WHERE id = :value")
    fun getNoteById(value: Int): Flow<NoteEntity>

    @Transaction
    @Query("SELECT * FROM note WHERE id = :value")
    fun getNoteRelationById(value: Int): Flow<NoteRelation>

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<NoteEntity>>
}