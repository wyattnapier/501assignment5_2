package com.example.assign5_2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Note(
    val id: Int,
    val text: String
)

class NotesViewModel : ViewModel() {
    var notes by mutableStateOf(listOf<Note>())
        private set

    var currentInput by mutableStateOf("")

    fun addNote() {
        if (currentInput.isNotBlank()) {
            val newNote = Note(id = notes.size + 1, text = currentInput)
            notes = notes + newNote
            currentInput = ""
        }
    }

    fun deleteNote(id: Int) {
        notes = notes.filterNot { it.id == id }
    }
}
