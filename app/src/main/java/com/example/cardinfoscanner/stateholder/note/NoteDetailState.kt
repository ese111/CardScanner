package com.example.cardinfoscanner.stateholder.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState

@Stable
class NoteDetailState(
    val uiState: BaseUiState,
    val note: State<Note>,
    val removeNote: (Note) -> Unit
) {
    val title = mutableStateOf(note.value.title)
    val content = mutableStateOf(note.value.content)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteDetailState(
    uiState: BaseUiState = rememberUiState(),
    note: State<Note> = remember { mutableStateOf(Note(title = "", content = "", date = "")) },
    removeNote: (Note) -> Unit = {}
) = remember(uiState, note) {
    NoteDetailState(
        uiState = uiState,
        note = note,
        removeNote = removeNote
    )
}