package com.example.cardinfoscanner.ui.note.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.ui.common.DropMenuState
import com.example.cardinfoscanner.ui.common.DropMenuTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteDetailScreen(
    uiState: BaseUiState = rememberUiState(),
    noteState: MutableState<Note> = remember { mutableStateOf(Note()) },
    removeNote:(Note) -> Unit = { _ -> },
    onClickUpButton: () -> Unit = {}
) {
    var dialogState: Boolean by remember { mutableStateOf(false) }
    var isTitleFocus: Boolean by remember { mutableStateOf(false) }
    var isContentFocus: Boolean by remember { mutableStateOf(false) }
    val isVisible = isContentFocus || isTitleFocus
    val focusManager = LocalFocusManager.current
    BackHandler(enabled = isVisible) {
        uiState.scope.launch {
            focusManager.clearFocus()
        }
    }
    if(dialogState) {
        NormalDialog(
            title = "정말로 삭제하시겠습니까?",
            phrase = "삭제된 데이터는 복구되지 않습니다.",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = {
                removeNote(noteState.value)
                dialogState = false },
            onDismiss = { dialogState = false }
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
                    DropMenuState("삭제하기") { dialogState = true }
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(20.dp)
        ) {
            TextField(
                value = noteState.value.title,
                onValueChange = { str ->
                    noteState.value = noteState.value.copy(title = str)
                },
                placeholder = {
                    Text(text = "제목 없음")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { state -> isTitleFocus = state.hasFocus },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )

            TextField(
                value = noteState.value.content,
                onValueChange = { str ->
                    noteState.value = noteState.value.copy(content = str)
                },
                placeholder = {
                    Text(text = "내용 없음")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .onFocusChanged { state -> isContentFocus = state.hasFocus },
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
}


@Preview(showBackground = true)
@Composable
private fun NoteDetailPreView() {
    NoteDetailScreen()
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailContentPreView() {}
