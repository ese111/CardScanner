package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import com.example.cardinfoscanner.data.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
) : ViewModel() {

    fun setNotesList(title: String, content: String) = viewModelScope.launch {
        noteRepository.setNoteList(
            Note(
                title = title,
                content = content,
                date = "${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date}"
            )
        )
    }

}