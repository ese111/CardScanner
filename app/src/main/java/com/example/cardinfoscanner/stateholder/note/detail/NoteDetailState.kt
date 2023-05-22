package com.example.cardinfoscanner.stateholder.note.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.common.TextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.ui.note.detail.NoteDetailUiState
import timber.log.Timber

@Stable
class NoteDetailState(
    navHostController: NavHostController,
    val removeDialogState: MutableState<Boolean>,
    val saveDialogState: MutableState<Boolean>,
    val titleFieldState: TextFieldState,
    val contentFieldState: TextFieldState,
    val noteDetailUiState: State<NoteDetailUiState>,
    private val save: (Note) -> Unit,
    private val remove: (Note) -> Unit
) {
    val isVisible = mutableStateOf(titleFieldState.isFocus.value || contentFieldState.isFocus.value)

    val note = when (val result = noteDetailUiState.value) {
        NoteDetailUiState.Loading -> Note()
        is NoteDetailUiState.Success -> result.data
    }

    val onTitleChanged = titleFieldState.onTextChange

    val onContentChange = contentFieldState.onTextChange

    val saveNote: () -> Unit = {
        saveDialogState.value = false
        save(
            note.copy(
                title = titleFieldState.value.value,
                content = contentFieldState.value.value
            )
        )
        navHostController.navigateUp()
    }

    val removeNote: () -> Unit = {
        removeDialogState.value = false
        remove(note)
        navHostController.navigateUp()
    }

    val isShowSaveDialog: (Boolean) -> Unit = { isShow -> saveDialogState.value = isShow }
    val isShowRemoveDialog: (Boolean) -> Unit = { isShow -> removeDialogState.value = isShow }

    val onContentFocusChanged: (Boolean) -> Unit =
        { isFocus -> contentFieldState.isFocus.value = isFocus }
    val onTitleFocusChanged: (Boolean) -> Unit =
        { isFocus -> titleFieldState.isFocus.value = isFocus }
}

@Composable
fun rememberNoteDetailState(
    navHostController: NavHostController = rememberNavController(),
    removeDialogState: MutableState<Boolean> = mutableStateOf(false),
    saveDialogState: MutableState<Boolean> = mutableStateOf(false),
    noteDetailUiState: State<NoteDetailUiState> = remember { mutableStateOf(NoteDetailUiState.Loading) },
    titleFieldState: TextFieldState = rememberTextFieldState(
        value = remember {
            when (val result = noteDetailUiState.value) {
                NoteDetailUiState.Loading -> mutableStateOf("")
                is NoteDetailUiState.Success -> mutableStateOf(result.data.title)
            }
        }
    ),
    contentFieldState: TextFieldState = rememberTextFieldState(
        value = remember {
            when (val result = noteDetailUiState.value) {
                NoteDetailUiState.Loading -> mutableStateOf("")
                is NoteDetailUiState.Success -> mutableStateOf(result.data.content)
            }
        }
    ),
    saveNote: (Note) -> Unit = {},
    removeNote: (Note) -> Unit = {}
) = remember(
    navHostController,
    removeDialogState,
    saveDialogState,
    titleFieldState,
    contentFieldState,
    noteDetailUiState,
    saveNote,
    removeNote
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