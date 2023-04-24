package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.stateholder.note.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
            noteRepository.getNotList().collectLatest {
                _noteList.value = it
            }
        }
    }

}