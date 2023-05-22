package com.example.cardinfoscanner.stateholder.note.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.common.TextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.ui.note.detail.NoteDetailUiState

@Stable
class NoteDetailState(
    navHostController: NavHostController,
    val removeDialogState: MutableState<Boolean>,
    val saveDialogState: MutableState<Boolean>,
    val titleFieldState: TextFieldState,
    val contentFieldState: TextFieldState,
    val noteDetailUiState: NoteDetailUiState,
    private val save: (Note) -> Unit,
    private val remove: (Note) -> Unit
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
    val saveNote: (Note) -> Unit = { note ->
        saveDialogState.value = false
        save(note)
        navHostController.navigateUp()
    }

    val removeNote: (Note) -> Unit = { note ->
        removeDialogState.value = false
        remove(note)
        navHostController.navigateUp()
    }

    val onDismissRemoveDialog = { removeDialogState.value = false }
    val onDismissSaveDialog = { saveDialogState.value = false }
    val showRemoveDialog = { removeDialogState.value = true }
    val showSaveDialog = { saveDialogState.value = true }

    val onContentFocusChanged: (Boolean) -> Unit = { isFocus -> contentFieldState.isFocus.value = isFocus }
    val onTitleFocusChanged: (Boolean) -> Unit = { isFocus -> titleFieldState.isFocus.value = isFocus }
}

@Composable
fun rememberNoteDetailState(
    navHostController: NavHostController = rememberNavController(),
    removeDialogState: MutableState<Boolean> = mutableStateOf(false),
    saveDialogState: MutableState<Boolean> = mutableStateOf(false),
    titleFieldState: TextFieldState = rememberTextFieldState(),
    contentFieldState: TextFieldState = rememberTextFieldState(),
    noteDetailUiState: NoteDetailUiState = NoteDetailUiState.Loading,
    saveNote: (Note) -> Unit = {},
    removeNote: (Note) -> Unit = {}
) = remember(
    removeDialogState,
    saveDialogState,
    titleFieldState,
    contentFieldState,
    noteDetailUiState
) {
    NoteDetailState(
        navHostController = navHostController,
        removeDialogState = removeDialogState,
        saveDialogState = saveDialogState,
        titleFieldState = titleFieldState,
        contentFieldState = contentFieldState,
        noteDetailUiState = noteDetailUiState,
        save = saveNote,
        remove = removeNote
    )
}