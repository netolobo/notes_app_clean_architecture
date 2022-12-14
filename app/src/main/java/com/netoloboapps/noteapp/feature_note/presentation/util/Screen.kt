package com.netoloboapps.noteapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_sreen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
}
