package com.example.notesapp.ui.screens.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.model.Note
import com.example.notesapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val noteId: String? = savedStateHandle.get<String>("noteId")
    private val categoryId: String? = savedStateHandle.get<String>("categoryId")

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    init {
        if (noteId != null) {
            viewModelScope.launch {
                repository.getNoteById(noteId).collect { _note.value = it }
            }
        }
    }

    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            val noteToSave = _note.value?.copy(title = title, content = content)
                ?: Note(id = noteId!!, title = title, content = content, categoryId = categoryId)
            repository.saveNote(noteToSave)
        }
    }
}
