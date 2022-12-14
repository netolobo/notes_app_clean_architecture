package com.netoloboapps.noteapp.feature_note.domain.use_case

import com.netoloboapps.noteapp.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int) = repository.getNoteById(id)
}