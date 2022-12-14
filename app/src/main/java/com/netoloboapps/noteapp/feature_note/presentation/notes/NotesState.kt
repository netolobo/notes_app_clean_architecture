package com.netoloboapps.noteapp.feature_note.presentation.notes

import com.netoloboapps.noteapp.feature_note.domain.model.Note
import com.netoloboapps.noteapp.feature_note.domain.util.NoteOrder
import com.netoloboapps.noteapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
