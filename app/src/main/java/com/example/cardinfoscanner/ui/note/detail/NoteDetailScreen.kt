package com.example.cardinfoscanner.ui.note.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.stateholder.note.NoteDetailState
import com.example.cardinfoscanner.stateholder.note.rememberNoteDetailState
import com.example.cardinfoscanner.ui.common.DropMenuState
import com.example.cardinfoscanner.ui.common.DropMenuTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun NoteDetailScreen(
    state: NoteDetailState = rememberNoteDetailState(),
    onClickUpButton: () -> Unit = {}
) {
    var dialogState: Boolean by remember { mutableStateOf(false) }
    var title: String by remember { state.title }
    var content: String by remember { state.content }
    var isTitleFocus: Boolean by remember { mutableStateOf(false) }
    var isContentFocus: Boolean by remember { mutableStateOf(false) }
    val isVisible = isContentFocus || isTitleFocus
    val focusManager = LocalFocusManager.current
    BackHandler(enabled = isVisible) {
        state.uiState.scope.launch {
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
                state.removeNote(state.note.value)
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
                value = title,
                onValueChange = { str ->
                    title = str
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
                value = content,
                onValueChange = { str ->
                    content = str
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

@Composable
private fun NoteDetailContent(
    content: String = "",
    onValueChange: (String) -> Unit = {}
) {

}


@Preview(showBackground = true)
@Composable
private fun NoteDetailPreView() {
    NoteDetailScreen()
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailContentPreView() {
    NoteDetailContent()
}
