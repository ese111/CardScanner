package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.data.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    fun setNotesList(note: Note) = viewModelScope.launch {
        noteRepository.setNoteList(note)
    }

    fun getNote(id: Long) = noteRepository.getNoteDetail(id)

}