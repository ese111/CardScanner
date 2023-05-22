package com.example.cardinfoscanner.ui.note.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cardinfoscanner.stateholder.note.edit.NoteEditState
import com.example.cardinfoscanner.stateholder.note.edit.rememberNoteEditState
import com.example.cardinfoscanner.ui.common.MenuTextTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteEditScreen(
    noteState: NoteEditState = rememberNoteEditState(),
    onClickSave: () -> Unit = {},
    onClickCancel: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onTitleFocusChanged: (Boolean) -> Unit = {},
    onContentFocusChanged: (Boolean) -> Unit = {},
    showSaveDialogState: () -> Unit = {},
    showCancelDialogState: () -> Unit = {},
    onDismissSaveDialog: () -> Unit = {},
    onDismissCancelDialog: () -> Unit = {}
) {
    BackHandler(enabled = noteState.isVisible.value) {
        noteState.uiState.scope.launch {
            noteState.uiState.focusManager.clearFocus()
        }
    }

    if(noteState.cancelDialogState.dialogState.value) {
        NormalDialog(
            title = "저장하지 않습니다.",
            phrase = "정말로 취소하시겠습니까?",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = {
                onDismissCancelDialog()
                onClickCancel()
            },
            onDismiss = onDismissCancelDialog
        )
    }
    if (noteState.saveDialogState.dialogState.value) {
        NormalDialog(
            title = "저장하기",
            phrase = "정말로 저장하시겠습니까?",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = { onClickSave() },
            onDismiss = onDismissSaveDialog
        )
    }

    Scaffold(
        topBar = {
            MenuTextTopAppBar(
                title = "Note",
                backButtonVisible = true,
                onClickBackButton = showCancelDialogState,
                menuText = "저장",
                onClickMenuButton = showSaveDialogState
            )
        }
    ) { paddingValues ->
        NoteEditorView(
            modifier = Modifier.padding(paddingValues),
            title = noteState.titleTextFieldState.value.value,
            content = noteState.contentTextFieldState.value.value,
            onTitleChange = onTitleChange,
            onContentChange = onContentChange,
            onTitleFocusChanged = onTitleFocusChanged,
            onContentFocusChanged = onContentFocusChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultNoteDetailPreview() {
    NoteEditScreen(onClickCancel = {}, onClickSave = {})
}