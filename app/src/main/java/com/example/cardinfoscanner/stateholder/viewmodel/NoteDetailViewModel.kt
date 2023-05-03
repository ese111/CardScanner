package com.example.cardinfoscanner.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

    fun getNoteDetail(id: Long) = noteRepository.getNoteDetail(id)

}