package com.example.cardinfoscanner.stateholder.note.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.DialogState
import com.example.cardinfoscanner.stateholder.common.TextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberDialogState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteListDestination
import timber.log.Timber

@Stable
class NoteEditState(
    val navHostController: NavHostController,
    val uiState: BaseUiState,
    val cancelDialogState: DialogState,
    val saveDialogState: DialogState,
    val titleTextFieldState: TextFieldState,
    val contentTextFieldState: TextFieldState,
    save: (String, String) -> Unit
) {
    val isVisible = mutableStateOf(titleTextFieldState.isFocus.value||contentTextFieldState.isFocus.value)
    val onSaveNote: () -> Unit = {
        saveDialogState.onChangeDialogState(false)
        Timber.i("Test : ${titleTextFieldState.value.value}, ${contentTextFieldState.value.value}")
        save(titleTextFieldState.value.value, contentTextFieldState.value.value)
        navHostController.navigateSingleTopToGraph(NoteListDestination.route)
    }
    val showSaveDialog = { saveDialogState.onChangeDialogState(true) }
    val showCancelDialog = { cancelDialogState.onChangeDialogState(true) }
    val onDismissSaveDialog = { saveDialogState.onChangeDialogState(false) }
    val onDismissCancelDialog = { cancelDialogState.onChangeDialogState(false) }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteEditState(
    navHostController: NavHostController = rememberNavController(),
    uiState: BaseUiState = rememberUiState(),
    cancelDialogState: DialogState = rememberDialogState(),
    saveDialogState: DialogState = rememberDialogState(),
    titleTextFieldState: TextFieldState = rememberTextFieldState(),
    contentTextFieldState: TextFieldState = rememberTextFieldState(),
    save: (String, String) -> Unit = { _, _ -> }
) = remember(
    navHostController,
    uiState,
    cancelDialogState,
    saveDialogState,
    titleTextFieldState,
    contentTextFieldState,
    save
) {
    NoteEditState(
        navHostController = navHostController,
        uiState = uiState,
        save = save,
        cancelDialogState = cancelDialogState,
        saveDialogState = saveDialogState,
        titleTextFieldState = titleTextFieldState,
        contentTextFieldState = contentTextFieldState
    )
}