package com.example.cardinfoscanner.ui.camera

import androidx.lifecycle.ViewModel
import com.example.cardinfoscanner.data.base.NoteRepository
import com.example.cardinfoscanner.data.local.repository.LocalNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val noteRepository: LocalNoteRepository
): ViewModel() {

}