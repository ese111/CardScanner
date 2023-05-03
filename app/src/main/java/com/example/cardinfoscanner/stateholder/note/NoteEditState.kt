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

sealed class EditScreenTyp {
    data class New(val key: String) : EditScreenTyp()
    data class Edite(val key: String) : EditScreenTyp()
}
@Stable
class NoteEditState(
    val uiState: BaseUiState,
    val setNote: (Note) -> Unit,
    val getNote: (Long) -> Note,
    private val bundle: Bundle?,
    private val type: EditScreenTyp
) {
    val note: MutableState<Note> = mutableStateOf(Note(title = "", content = "", date = ""))
    val content: MutableState<String> = mutableStateOf("")
    val title: MutableState<String> = mutableStateOf("")
    init {
        when(type) {
            is EditScreenTyp.Edite -> {
                bundle?.getLong(type.key)?.let {
                    getNote(it).also { data ->
                        note.value = data
                        content.value = data.content
                        title.value = data.title
                    }

                }
            }
            is EditScreenTyp.New -> {
                bundle?.getString(type.key)?.let { scanText ->
                    content.value = scanText.replace("+", "/")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteState(
    uiState: BaseUiState = rememberUiState(),
    type: EditScreenTyp = EditScreenTyp.New(""),
    bundle: Bundle? = null,
    setNote: (Note) -> Unit = {},
    getNote: (Long) -> Note = { _ -> Note()}
) = remember(uiState, type, bundle, setNote, getNote) {
    NoteEditState(
        uiState = uiState,
        setNote = setNote,
        getNote = getNote,
        bundle = bundle,
        type = type
    )
}