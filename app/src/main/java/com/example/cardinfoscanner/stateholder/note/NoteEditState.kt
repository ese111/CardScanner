package com.example.cardinfoscanner.stateholder.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState

@Stable
class NoteEditState(
    val uiState: BaseUiState,
    val content: MutableState<String>,
    val title: MutableState<String>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteState(
    uiState: BaseUiState = rememberUiState(),
    content: MutableState<String> = mutableStateOf(""),
    title: MutableState<String> = mutableStateOf("")
) = remember(uiState, content, title) {
    NoteEditState(
        uiState = uiState,
        content = content,
        title = title
    )
}