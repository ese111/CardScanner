package com.example.cardinfoscanner.ui.note.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cardinfoscanner.stateholder.note.edit.NoteWriteState
import com.example.cardinfoscanner.stateholder.note.edit.rememberNoteWriteState
import com.example.cardinfoscanner.ui.common.MenuTextTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch

@Composable
fun NoteWriteScreen(
    noteState: NoteWriteState = rememberNoteWriteState(),
    saveNote:() -> Unit = {},
    onChangeCancelDialog: (Boolean) -> Unit = {},
    onChangeSaveDialog: (Boolean) -> Unit = {},
    onClickUpButton: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onTitleFocusChanged: (Boolean) -> Unit = {},
    onContentFocusChanged: (Boolean) -> Unit = {}
) {
    BackHandler(enabled = noteState.isVisible.value) {
        noteState.uiState.scope.launch {
            noteState.uiState.focusManager.clearFocus()
        }
    }

    if(noteState.saveDialogState.dialogState.value) {
        NormalDialog(
            title = "저장하시겠습니까?",
            phrase = "메모는 수정, 삭제가 가능합니다.",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = saveNote,
            onDismiss = { onChangeSaveDialog(false) }
        )
    }
    if(noteState.cancelDialogState.dialogState.value) {
        NormalDialog(
            title = "작성을 취소하시겠습니까?",
            phrase = "저장되지 않은 메모는 복구되지 않습니다.",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = onClickUpButton,
            onDismiss = { onChangeCancelDialog(false) }
        )
    }

    Scaffold(
        topBar = {
            MenuTextTopAppBar(
                title = "NoteWrite",
                backButtonVisible = true,
                menuText = "저장",
                onClickBackButton = { onChangeCancelDialog(true) },
                onClickMenuButton = { onChangeSaveDialog(true) }
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