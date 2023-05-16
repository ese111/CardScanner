package com.example.cardinfoscanner.ui.note.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.note.detail.NoteDetailState
import com.example.cardinfoscanner.stateholder.note.detail.rememberNoteDetailState
import com.example.cardinfoscanner.ui.common.DropMenuState
import com.example.cardinfoscanner.ui.common.DropMenuTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch

sealed interface NoteDetailUiState {
    object Loading : NoteDetailUiState

    data class Success(val data: Note) : NoteDetailUiState
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteDetailScreen(
    uiState: BaseUiState = rememberUiState(),
    noteState: NoteDetailState = rememberNoteDetailState(),
    removeNote:(Note) -> Unit = {},
    saveNote:(Note) -> Unit = {},
    onDismissRemoveDialog: () -> Unit = {},
    onDismissSaveDialog: () -> Unit = {},
    showRemoveDialog: () -> Unit = {},
    showSaveDialog: () -> Unit = {},
    onClickUpButton: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onTitleFocusChanged: (Boolean) -> Unit = {},
    onContentFocusChanged: (Boolean) -> Unit = {}
) {

    BackHandler(enabled = noteState.isVisible.value) {
        uiState.scope.launch {
            uiState.focusManager.clearFocus()
        }
    }

    if(noteState.saveDialogState.value) {
        NormalDialog(
            title = "변경 사항을 저장하시겠습니까?",
            phrase = "변경된 데이터는 복구되지 않습니다.",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = { saveNote(noteState.note.value) },
            onDismiss = onDismissSaveDialog
        )
    }
    if(noteState.removeDialogState.value) {
        NormalDialog(
            title = "정말로 삭제하시겠습니까?",
            phrase = "삭제된 데이터는 복구되지 않습니다.",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = { removeNote(noteState.note.value) },
            onDismiss = onDismissRemoveDialog
        )
    }

    Scaffold(
        topBar = {
            DropMenuTopAppBar(
                title = "NoteDetail",
                backButtonVisible = true,
                menuIcon = rememberVectorPainter(Icons.Default.Menu),
                onClickBackButton = onClickUpButton,
                dropMenuItems = listOf(
                    DropMenuState(
                        name = "저장하기",
                        onClick = showSaveDialog
                    ),
                    DropMenuState(
                        name = "삭제하기",
                        onClick = showRemoveDialog
                    )
                )
            )
        }
    ) { paddingValues ->
        NoteEditorView(
            modifier = Modifier.padding(paddingValues),
            title = noteState.note.value.title,
            content = noteState.note.value.content,
            onTitleChange = onTitleChange,
            onContentChange = onContentChange,
            onTitleFocusChanged = onTitleFocusChanged,
            onContentFocusChanged = onContentFocusChanged
        )
    }
}

@Composable
fun NoteEditorView(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    onTitleFocusChanged: (Boolean) -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onContentFocusChanged: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            placeholder = {
                Text(text = "제목 없음")
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state -> onTitleFocusChanged(state.hasFocus) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        TextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = {
                Text(text = "내용 없음")
            },
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { state -> onContentFocusChanged(state.hasFocus) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun NoteDetailPreView() {
    NoteDetailScreen()
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailContentPreView() {}
