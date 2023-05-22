package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.note.detail.rememberNoteDetailState
import com.example.cardinfoscanner.stateholder.note.edit.rememberNoteEditState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteDetailViewModel
import com.example.cardinfoscanner.stateholder.viewmodel.NoteEditViewModel
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.detail.NoteDetailScreen
import com.example.cardinfoscanner.ui.note.edit.NoteEditScreen
import com.example.cardinfoscanner.ui.note.list.NoteListScreen
import timber.log.Timber

object NotesDestination: Destination {
    override val route = Destination.noteListRout
    @OptIn(ExperimentalComposeUiApi::class)
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            val noteListState = viewModel.noteList.collectAsStateWithLifecycle(initialValue = emptyList())
            Timber.tag("AppTest").d("noteListState : $noteListState")
            NoteListScreen(
                uiState = rememberUiState(),
                noteListState = noteListState,
                moveToCamera = { navController.navigateSingleTopTo(Destination.cameraRoute) },
                moveToEdit = {},
                onClickNote = { id -> navController.navigateSingleTopTo("${NoteDetailDestination.route}/$id") },
                onCancelRemove = viewModel::cancelRemove,
                onRemoveNote = viewModel::removeNote
            )
        }
    }
}

object NoteEditDestination: Destination {
    override val route = Destination.noteEditRout
    private const val resultKey = "result"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.StringType }
    )
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, bundle, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteEditViewModel = hiltViewModel(it)
            bundle?.getString(resultKey)?.let { scanText ->
                val noteEditState = rememberNoteEditState(
                    navHostController = navController,
                    save = viewModel::setNotesList,
                    contentTextFieldState = rememberTextFieldState(
                        value = mutableStateOf(scanText.replace("+", "/"))
                    )
                )

                NoteEditScreen(
                    noteState = noteEditState,
                    onClickSave = noteEditState.onSaveNote,
                    onClickCancel = navController::navigateUp,
                    onTitleChange = noteEditState.titleTextFieldState.onTextChange,
                    onContentChange = noteEditState.contentTextFieldState.onTextChange,
                    onTitleFocusChanged = noteEditState.titleTextFieldState.onChangeFocus,
                    onContentFocusChanged = noteEditState.contentTextFieldState.onChangeFocus,
                    showSaveDialogState = noteEditState.showSaveDialog,
                    showCancelDialogState = noteEditState.showCancelDialog,
                    onDismissSaveDialog = noteEditState.onDismissSaveDialog,
                    onDismissCancelDialog = noteEditState.onDismissCancelDialog
                )
            }
        }
    }
}

object NoteDetailDestination: Destination {
    override val route = Destination.noteDetailRout
    private const val resultKey = "note_id"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.LongType }
    )
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, bundle, _ ->
        navController.currentBackStackEntry?.let {
            bundle?.getLong(resultKey)?.let { id ->
                val viewModel: NoteDetailViewModel = hiltViewModel(it)
                val noteState = rememberNoteDetailState(
                    noteDetailUiState = viewModel.getNoteDetail(id),
                    saveNote = viewModel::saveNote,
                    removeNote = viewModel::removeNote
                )

                NoteDetailScreen(
                    noteState = noteState,
                    removeNote = noteState.removeNote,
                    saveNote = noteState.saveNote,
                    onClickUpButton = navController::navigateUp,
                    onDismissRemoveDialog = noteState.onDismissRemoveDialog,
                    onDismissSaveDialog = noteState.onDismissSaveDialog,
                    showRemoveDialog = noteState.showRemoveDialog,
                    showSaveDialog = noteState.showSaveDialog,
                    onContentFocusChanged = noteState.onContentFocusChanged,
                    onTitleFocusChanged = noteState.onTitleFocusChanged,
                    onContentChange = noteState.onContentChange,
                    onTitleChange = noteState.onTitleChanged
                )
            }
        }
    }
}