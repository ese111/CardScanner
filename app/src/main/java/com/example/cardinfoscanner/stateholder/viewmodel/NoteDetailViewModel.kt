package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.ui.note.detail.NoteDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {
    private val _noteState: MutableStateFlow<NoteDetailUiState> = MutableStateFlow(NoteDetailUiState.Loading)
    val noteState = _noteState.asStateFlow()
    fun getNoteDetail(id: Long) { _noteState.value = noteRepository.getNoteDetail(id) }

    fun removeNote(note: Note) = viewModelScope.launch { noteRepository.removeNote(note) }
    fun saveNote(note: Note) = viewModelScope.launch { noteRepository.saveNote(note) }
}
