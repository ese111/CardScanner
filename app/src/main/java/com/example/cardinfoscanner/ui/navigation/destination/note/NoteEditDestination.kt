package com.example.cardinfoscanner.ui.navigation.destination.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.common.rememberTextFieldState
import com.example.cardinfoscanner.stateholder.note.edit.rememberNoteEditState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteEditViewModel
import com.example.cardinfoscanner.ui.note.edit.NoteEditScreen

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