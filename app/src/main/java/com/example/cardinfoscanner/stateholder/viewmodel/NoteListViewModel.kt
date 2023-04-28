package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    val noteList = noteRepository.getNotList()

}
