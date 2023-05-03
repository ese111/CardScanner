package com.example.cardinfoscanner.ui.note.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.stateholder.note.Note
import com.example.cardinfoscanner.stateholder.note.NoteEditState
import com.example.cardinfoscanner.stateholder.note.rememberNoteState
import com.example.cardinfoscanner.ui.common.MenuTextTopAppBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun NoteEditScreen(
    state: NoteEditState = rememberNoteState(),
    onClickSave: () -> Unit = {},
    onClickCancel: () -> Unit = {}
) {
    var dialogState by remember { mutableStateOf(false) }
    var saveDialog by remember { mutableStateOf(false) }

    if(dialogState) {
        NormalDialog(
            title = "저장하지 않습니다.",
            phrase = "정말로 취소하시겠습니까?",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = {
                dialogState = false
                onClickCancel()
            },
            onDismiss = { dialogState = false }
        )
    }
    if (saveDialog) {
        NormalDialog(
            title = "저장하기",
            phrase = "정말로 저장하시겠습니까?",
            confirmText = "확인",
            dismissText = "취소",
            onConfirm = {
                saveDialog = false
                state.setNote(
                    Note(
                        title = state.title.value,
                        content = state.content.value,
                        date = "${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date}"
                    )
                )
                onClickSave()
            },
            onDismiss = { saveDialog = false }
        )
    }
    Scaffold(
        topBar = {
            MenuTextTopAppBar(
                title = "Note",
                backButtonVisible = true,
                onClickBackButton = { dialogState = true },
                menuText = "저장",
                onClickMenuButton = { saveDialog = true }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            NoteTitleEditor(text = state.title.value, placeHolder = "제목 없음") { value ->
                state.title.value = value
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
            NoteContentEditor(text = state.content.value, placeHolder = "내용") { value ->
                state.content.value = value
            }
        }
    }
}

@Composable
private fun NoteTitleEditor(
    text: String = "",
    placeHolder: String = "",
    onValueChange: (String) -> Unit = { _ -> }
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            ),
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeHolder)
        },
        shape = RoundedCornerShape(16.dp),
        maxLines = 1
    )
}

@Composable
private fun NoteContentEditor(
    text: String = "",
    placeHolder: String = "",
    onValueChange: (String) -> Unit = { _ -> }
) {
    TextField(
        modifier = Modifier
            .fillMaxSize()
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            ),
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeHolder)
        },
        shape = RoundedCornerShape(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun TitleEditorPreview() {
    NoteTitleEditor()
}

@Preview(showBackground = true)
@Composable
fun ContentEditorPreview() {
    NoteContentEditor()
}

@Preview(showBackground = true)
@Composable
fun DefaultNoteDetailPreview() {
    NoteEditScreen(onClickCancel = {}, onClickSave = {})
}