package com.netoloboapps.noteapp.feature_note.data.repository

import com.netoloboapps.noteapp.feature_note.data.data_source.NoteDao
import com.netoloboapps.noteapp.feature_note.domain.model.Note
import com.netoloboapps.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    override suspend fun inserteNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)
}