package com.funnypaper.simme.data.local.repository.note

import com.funnypaper.simme.data.local.dao.NoteDao
import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.relation.NoteRelation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDataNoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : IDataNoteRepository {
    override fun getAllNotes(): Flow<List<NoteEntity>> =
        noteDao.getAllNotes()
    override fun getNote(id: Int): Flow<NoteEntity> =
        noteDao.getNoteById(id)
    override fun getNoteRelation(id: Int): Flow<NoteRelation> =
        noteDao.getNoteRelationById(id)
    override suspend fun updateNote(value: NoteEntity) =
        noteDao.update(value)
    override suspend fun insertNote(value: NoteEntity): Long =
        noteDao.insert(value)
    override suspend fun deleteNote(value: NoteEntity) =
        noteDao.delete(value)
}