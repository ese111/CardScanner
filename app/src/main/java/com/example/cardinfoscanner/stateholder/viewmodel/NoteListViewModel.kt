package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.stateholder.note.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    private val _noteList = MutableStateFlow(emptyList<Note>())
    val noteList = _noteList.asStateFlow()

    private val onLoadCeh = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("$coroutineContext onLoad NoteList Error ${throwable.message}")
        _noteList.value = emptyList()
    }

    init {
        viewModelScope.launch(onLoadCeh) {
            noteRepository.getNotList().collect {
                _noteList.value = it
            }
        }
    }

    fun setNotesList(note: Note) = viewModelScope.launch {
        val list = mutableListOf<Note>()
        list.addAll(noteList.value)
        val newNote = note.copy(id = list.size.toLong())
        list.add(newNote)
        Timber.tag("AppTest").d("setNotesList : $list")
        noteRepository.setNoteList(list)
    }

}
