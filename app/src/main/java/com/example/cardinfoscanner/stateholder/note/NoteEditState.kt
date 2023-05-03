package com.example.cardinfoscanner.stateholder.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.ui.navigation.destination.NoteEditDestination
import java.util.ListResourceBundle


@Stable
class NoteEditState(
    val uiState: BaseUiState,
    val setNote: (Note) -> Unit,
    val note: MutableState<Note>
) {
    val content: MutableState<String> = mutableStateOf("")
    val title: MutableState<String> = mutableStateOf("")

    init {
        content.value = note.value.content
        title.value = note.value.title
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteState(
    uiState: BaseUiState = rememberUiState(),
    note: MutableState<Note> = remember { mutableStateOf(Note()) },
    setNote: (Note) -> Unit = {}
) = remember(uiState, setNote) {
    NoteEditState(
        uiState = uiState,
        setNote = setNote,
        note = note
    )
}