package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.note.list.rememberNoteItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    val noteList = noteRepository.getNotList()

    fun removeNote(note: Note) {
        viewModelScope.launch {
            Timber.i("removeNote : ${note.title}")
            noteRepository.removeNote(note)
        }
    }

    fun removeAllNote(list: List<Note>) {
        viewModelScope.launch {
            Timber.i("removeAllNote : $list")
            noteRepository.removeAllNote(list)
        }
    }

    fun cancelRemove() = viewModelScope.launch { noteRepository.cancelRemove() }

}
