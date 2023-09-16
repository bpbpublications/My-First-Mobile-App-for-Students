package com.example.flashcards.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.Note
import com.example.flashcards.data.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDao
) : ViewModel() {

    val allNotes: Flow<List<Note>> = dao.getAllNotes()

    private val _selectedNote = MutableLiveData<Note>()
    val selectedNote get() = _selectedNote

    fun select(note: Note) {
        _selectedNote.value = note
    }

    fun getNote(): Flow<Note> {
        return dao.getNoteById(selectedNote.value!!.id)
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            dao.insert(note)
        }
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        dao.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        dao.delete(note)
    }

    private fun getNewNote(noteTitle: String, noteContent: String): Note {
        return Note(title = noteTitle, content = noteContent)
    }

    fun isEntryValid(noteTitle: String, noteContent: String): Boolean {
        if (noteTitle.isBlank() || noteContent.isBlank()) {
            return false
        }
        return true
    }

    fun addNote(noteTitle: String, noteContent: String) {
        val note = getNewNote(noteTitle, noteContent)
        insertNote(note)
    }


    fun editNote(noteId: Long, noteTitle: String, noteContent: String) {
        val note = getUpdatedNote(
            noteId,
            noteTitle,
            noteContent
        )
        updateNote(note)
    }

    private fun getUpdatedNote(noteId: Long, noteTitle: String, noteContent: String): Note {
        return Note(
            id = noteId,
            title = noteTitle,
            content = noteContent
        )
    }


}

// boilerplate code for the ViewModelFactory
class NoteViewModelFactory(
    private val dao: NoteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return NoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}