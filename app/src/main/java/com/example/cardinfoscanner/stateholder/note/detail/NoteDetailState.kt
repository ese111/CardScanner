package com.example.cardinfoscanner.stateholder.note.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.common.TextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.ui.note.detail.NoteDetailUiState

@Stable
class NoteDetailState(
    val removeDialogState: MutableState<Boolean>,
    val saveDialogState: MutableState<Boolean>,
    val titleFieldState: TextFieldState,
    val contentFieldState: TextFieldState,
    val noteDetailUiState: NoteDetailUiState
) {
    val isVisible = mutableStateOf(titleFieldState.isFocus.value || contentFieldState.isFocus.value)
    val note = when(noteDetailUiState) {
        NoteDetailUiState.Loading -> mutableStateOf(Note())
        is NoteDetailUiState.Success -> mutableStateOf(noteDetailUiState.data)
    }
    val onTitleChanged: (String) -> Unit = { str ->
        note.value = note.value.copy(title = str)
    }
    val onContentChange: (String) -> Unit = { str ->
        note.value = note.value.copy(content = str)
    }
}

@Composable
fun rememberNoteDetailState(
    removeDialogState: MutableState<Boolean> = mutableStateOf(false),
    saveDialogState: MutableState<Boolean> = mutableStateOf(false),
    titleFieldState: TextFieldState = rememberTextFieldState(),
    contentFieldState: TextFieldState = rememberTextFieldState(),
    noteDetailUiState: NoteDetailUiState = NoteDetailUiState.Loading
) = remember(
    removeDialogState,
    saveDialogState,
    titleFieldState,
    contentFieldState,
    noteDetailUiState
) {
    NoteDetailState(
        removeDialogState = removeDialogState,
        saveDialogState = saveDialogState,
        titleFieldState = titleFieldState,
        contentFieldState = contentFieldState,
        noteDetailUiState = noteDetailUiState
    )
}