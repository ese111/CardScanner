package com.example.cardinfoscanner.stateholder.note.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.DialogState
import com.example.cardinfoscanner.stateholder.common.TextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberDialogState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberUiState

@Stable
class NoteWriteState(
    val navHostController: NavHostController,
    val uiState: BaseUiState,
    val cancelDialogState: DialogState,
    val saveDialogState: DialogState,
    val titleTextFieldState: TextFieldState,
    val contentTextFieldState: TextFieldState,
    save: (String, String) -> Unit
) {
    val isVisible = mutableStateOf(titleTextFieldState.isFocus.value || contentTextFieldState.isFocus.value)

    val onChangeTitle: (String) -> Unit = titleTextFieldState.onTextChange

    val onChangeContent: (String) -> Unit = contentTextFieldState.onTextChange

    val onSaveNote:() -> Unit = {
        saveDialogState.onChangeDialogState(false)
        save(titleTextFieldState.value.value, contentTextFieldState.value.value)
        navHostController.navigateUp()
    }

    val onCancel:() -> Unit = {
        cancelDialogState.onChangeDialogState(false)
        navHostController.navigateUp()
    }

    val onContentFocusChanged: (Boolean) -> Unit = contentTextFieldState.onChangeFocus

    val onTitleFocusChanged: (Boolean) -> Unit = titleTextFieldState.onChangeFocus

    val isShowSaveDialog: (Boolean) -> Unit = saveDialogState.onChangeDialogState

    val iShowCancelDialog: (Boolean) -> Unit = cancelDialogState.onChangeDialogState

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteWriteState(
    navHostController: NavHostController = rememberNavController(),
    uiState: BaseUiState = rememberUiState(),
    cancelDialogState: DialogState = rememberDialogState(),
    saveDialogState: DialogState = rememberDialogState(),
    titleTextFieldState: TextFieldState = rememberTextFieldState(),
    contentTextFieldState: TextFieldState = rememberTextFieldState(),
    save: (String, String) -> Unit = {_, _ -> }
) = remember(
    navHostController,
    cancelDialogState,
    saveDialogState,
    titleTextFieldState,
    contentTextFieldState,
    save
) {
    NoteWriteState(
        navHostController = navHostController,
        uiState = uiState,
        cancelDialogState = cancelDialogState,
        saveDialogState = saveDialogState,
        titleTextFieldState = titleTextFieldState,
        contentTextFieldState = contentTextFieldState,
        save = save
    )
}