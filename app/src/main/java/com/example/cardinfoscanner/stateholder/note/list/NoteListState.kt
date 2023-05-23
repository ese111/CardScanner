package com.example.cardinfoscanner.stateholder.note.list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
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

@Stable
class NoteListState(
    val uiState: BaseUiState,
    val noteListState: State<List<Note>>,
    val navHostController: NavHostController,
    val onRemoveNote: (Note) -> Unit,
    val onCancelRemove: () -> Unit,
    val snackBarHostState: SnackbarHostState
) {
    val moveToCamera: () -> Unit = {
        navHostController.navigateSingleTopToGraph(Destination.cameraRoute)
    }
    val moveToEdit: () -> Unit = {
        navHostController.navigateSingleTopToGraph(Destination.noteWriteRout)
    }
    val onClickNote: (Long) -> Unit = {id -> navHostController.navigateSingleTopToGraph("${NoteDetailDestination.route}/$id") }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberNoteListState(
    uiState: BaseUiState = rememberUiState(),
    noteListState: State<List<Note>> = remember { mutableStateOf(emptyList()) },
    navHostController: NavHostController = rememberNavController(),
     onRemoveNote: (Note) -> Unit = {},
     onCancelRemove: () -> Unit = {},
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(
    uiState,
    noteListState,
    navHostController,
    onRemoveNote,
    onCancelRemove
){
    NoteListState(
        uiState = uiState,
        noteListState = noteListState,
        navHostController = navHostController,
        onRemoveNote = onRemoveNote,
        onCancelRemove = onCancelRemove,
        snackBarHostState = snackBarHostState
    )
}