package com.example.cardinfoscanner.ui.navigation.destination.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.note.detail.rememberNoteDetailState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteDetailViewModel
import com.example.cardinfoscanner.ui.note.detail.NoteDetailScreen

object NoteDetailDestination : Destination {
    override val route = Destination.noteDetailRout
    private const val resultKey = "note_id"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.LongType }
    )
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit =
        { navController, bundle, _ ->
            navController.currentBackStackEntry?.let {
                bundle?.getLong(resultKey)?.let { id ->
                    val viewModel: NoteDetailViewModel = hiltViewModel(it)
                    viewModel.getNoteDetail(id)
                    val noteState = rememberNoteDetailState(
                        navHostController = navController,
                        noteDetailUiState = viewModel.note.collectAsStateWithLifecycle(),
                        saveNote = viewModel::saveNote,
                        removeNote = viewModel::removeNote
                    )

                    NoteDetailScreen(
                        noteState = noteState,
                        removeNote = noteState.removeNote,
                        saveNote = noteState.saveNote,
                        onClickUpButton = navController::navigateUp,
                        isShowRemoveDialog = noteState.isShowRemoveDialog,
                        isShowSaveDialog = noteState.isShowSaveDialog,
                        onContentFocusChanged = noteState.onContentFocusChanged,
                        onTitleFocusChanged = noteState.onTitleFocusChanged,
                        onContentChange = noteState.onContentChange,
                        onTitleChange = noteState.onTitleChanged
                    )
                }
            }
        }
}