package com.example.cardinfoscanner.stateholder.note.list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteDetailDestination
import timber.log.Timber

@Stable
class NoteListState(
    val uiState: BaseUiState,
    val noteListState: State<List<NoteItemState>>,
    val navHostController: NavHostController,
    val onRemoveNote: (Note) -> Unit,
    val onCancelRemove: () -> Unit,
    val snackBarHostState: SnackbarHostState,
    val itemSelectModeState: MutableState<Boolean>,
    val selectItemCount: MutableState<Int>,
    onRemoveAllNote: (List<Note>) -> Unit
) {
    val moveToCamera: () -> Unit = {
        navHostController.navigateSingleTopToGraph(Destination.cameraRoute)
    }
    val moveToEdit: () -> Unit = {
        navHostController.navigateSingleTopToGraph(Destination.noteWriteRout)
    }
    val onClickNote: (Long) -> Unit = {id -> navHostController.navigateSingleTopToGraph("${NoteDetailDestination.route}/$id") }
    val onChangeSelectCount = { isSelected: Boolean ->
        if (isSelected) {
            selectItemCount.value++
        } else {
            selectItemCount.value--
        }
        Timber.i("onChangeSelectCount : $isSelected ${selectItemCount.value}")
        if(selectItemCount.value == 0) {
            onClearSelectMode()
        }
    }
    val onLongClickItem: (Boolean) -> Unit = { isSelected ->
        onChangeSelectCount(isSelected)
        itemSelectModeState.value = isSelected
    }
    val onClearSelectMode = {
        noteListState.value
            .filter { it.isSelected.value }
            .forEach { it.onCheckedChange(false) }
        selectItemCount.value = 0
        itemSelectModeState.value = false
    }
    val onRemoveAll = {
        onRemoveAllNote(noteListState.value.filter { it.isSelected.value }.map { it.note })
        onClearSelectMode()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteListState(
    uiState: BaseUiState = rememberUiState(),
    noteListState: State<List<NoteItemState>> = remember { mutableStateOf(emptyList()) },
    navHostController: NavHostController = rememberNavController(),
    onRemoveNote: (Note) -> Unit = {},
    onRemoveAllNote: (List<Note>) -> Unit = {},
    onCancelRemove: () -> Unit = {},
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    itemSelectModeState: MutableState<Boolean> = remember { mutableStateOf(false) },
    selectItemCount: MutableState<Int> = remember { mutableStateOf(0) }
) = remember(
    uiState,
    noteListState,
    navHostController,
    onRemoveNote,
    onCancelRemove,
    snackBarHostState,
    itemSelectModeState,
    onRemoveAllNote,
    selectItemCount
){
    NoteListState(
        uiState = uiState,
        noteListState = noteListState,
        navHostController = navHostController,
        onRemoveNote = onRemoveNote,
        onRemoveAllNote = onRemoveAllNote,
        onCancelRemove = onCancelRemove,
        snackBarHostState = snackBarHostState,
        itemSelectModeState = itemSelectModeState,
        selectItemCount = selectItemCount
    )
}