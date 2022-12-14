package com.netoloboapps.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.netoloboapps.noteapp.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            Purple80,
            PurpleGrey80,
            Purple40,
            Pink80,
            Pink40
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
