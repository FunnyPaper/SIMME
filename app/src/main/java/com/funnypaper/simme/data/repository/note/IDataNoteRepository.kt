package com.funnypaper.simme.data.repository.note

import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.relation.NoteRelation
import kotlinx.coroutines.flow.Flow

interface IDataNoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNote(id: Int): Flow<NoteEntity>
    fun getNoteRelation(id: Int): Flow<NoteRelation>
    suspend fun updateNote(value: NoteEntity)
    suspend fun insertNote(value: NoteEntity): Long
    suspend fun deleteNote(value: NoteEntity)
}