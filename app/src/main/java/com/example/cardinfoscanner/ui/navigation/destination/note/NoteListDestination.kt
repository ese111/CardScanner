package com.example.cardinfoscanner.ui.navigation.destination.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.note.list.rememberNoteItemState
import com.example.cardinfoscanner.stateholder.note.list.rememberNoteListState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.list.NoteListScreen
import kotlinx.coroutines.flow.map
import timber.log.Timber

object NoteListDestination: Destination {
    override val route = Destination.noteListRout
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            val noteList = viewModel.noteList
                .collectAsStateWithLifecycle(initialValue = emptyList())
                .value
                .map { note ->
                    Timber.i("note : $note")
                    rememberNoteItemState(note = note)
                }
            NoteListScreen(
                noteListState = rememberNoteListState(
                    navHostController = navController,
                    noteListState = mutableStateOf(noteList),
                    onCancelRemove = viewModel::cancelRemove,
                    onRemoveNote = viewModel::removeNote,
                    onRemoveAllNote = viewModel::removeAllNote
                )
            )
        }
    }
}