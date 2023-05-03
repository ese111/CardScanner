package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.stateholder.note.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    fun getNoteDetail(id: Long) = noteRepository.getNoteDetail(id)

    fun removeNote(note: Note) = viewModelScope.launch { noteRepository.removeNote(note) }

}