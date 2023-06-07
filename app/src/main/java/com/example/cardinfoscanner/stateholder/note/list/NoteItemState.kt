package com.example.cardinfoscanner.stateholder.note.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cardinfoscanner.data.local.model.Note

@Stable
class NoteItemState(
    val note: Note,
    val isSelected: MutableState<Boolean>
) {
    val onCheckedChange: (Boolean) -> Unit = { isChecked -> isSelected.value = isChecked }
}

@Composable
fun rememberNoteItemState(
    note: Note = remember { Note() },
    isSelected: MutableState<Boolean> = remember { mutableStateOf(false) }
) = remember(note, isSelected) {
    NoteItemState(
        note = note,
        isSelected = isSelected
    )
}