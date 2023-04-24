package com.example.cardinfoscanner.stateholder.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import kotlinx.coroutines.flow.StateFlow

@Stable
data class NoteListState(
    val uiState: BaseUiState,
    val noteList: StateFlow<List<Note>>
)

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val date: String
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteListState(
    uiState: BaseUiState = rememberUiState(),
    noteList: StateFlow<List<Note>>
) = remember(noteList) {
    NoteListState(
        uiState = uiState,
        noteList = noteList
    )
}