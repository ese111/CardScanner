package com.example.cardinfoscanner.ui.navigation.destination.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.note.edit.rememberNoteWriteState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteEditViewModel
import com.example.cardinfoscanner.ui.note.edit.NoteWriteScreen

object NoteWriteDestination: Destination {
    override val route = Destination.noteWriteRout
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteEditViewModel = hiltViewModel(it)
            val noteWriteState = rememberNoteWriteState(
                navHostController = navController,
                save = viewModel::setNotesList
            )

            NoteWriteScreen(
                noteState = noteWriteState,
                saveNote = noteWriteState.onSaveNote,
                onClickUpButton = noteWriteState.onCancel,
                onChangeSaveDialog = noteWriteState.isShowSaveDialog,
                onChangeCancelDialog = noteWriteState.iShowCancelDialog,
                onContentFocusChanged = noteWriteState.onContentFocusChanged,
                onTitleFocusChanged = noteWriteState.onTitleFocusChanged,
                onContentChange = noteWriteState.onChangeContent,
                onTitleChange = noteWriteState.onChangeTitle
            )
        }
    }
}