package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.note.EditScreenTyp
import com.example.cardinfoscanner.stateholder.note.rememberNoteDetailState
import com.example.cardinfoscanner.stateholder.note.rememberNoteListState
import com.example.cardinfoscanner.stateholder.note.rememberNoteState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteDetailViewModel
import com.example.cardinfoscanner.stateholder.viewmodel.NoteEditViewModel
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.detail.NoteDetailScreen
import com.example.cardinfoscanner.ui.note.edit.NoteEditScreen
import com.example.cardinfoscanner.ui.note.list.NoteListScreen

object NotesDestination: Destination {
    override val route = Destination.noteListRout
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            val noteListState = viewModel.noteList.collectAsStateWithLifecycle(initialValue = emptyList())
            NoteListScreen(
                state = rememberNoteListState(
                    noteList = noteListState,
                    onRemoveNote = viewModel::removeNote,
                    onCancelRemove = viewModel::cancelRemove
                ),
                onClickMenuButton = { navController.navigateSingleTopTo(Destination.cameraRoute) },
                onClickNote = { id -> navController.navigateSingleTopTo("${NoteDetailDestination.route}/$id") }
            )
        }
    }
}

object NoteEditDestination: Destination {
    override val route = Destination.noteEditRout
    private const val resultKey = "result"
    private const val selectId = "note_id"
    val routeWithArgs = "$route/{$resultKey}"
    val routeWithId = "$route/{$selectId}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.StringType },
        navArgument(selectId) { type = NavType.LongType }
    )
    var type: EditScreenTyp = EditScreenTyp.New(resultKey)
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, bundle, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteEditViewModel = hiltViewModel(it)
            NoteEditScreen(
                state = rememberNoteState(
                    type = type,
                    bundle = bundle,
                    setNote = viewModel::setNotesList,
                    getNote = viewModel::getNote
                ),
                onClickSave = {
                    navController.navigateSingleTopTo(NotesDestination.route)
                },
                onClickCancel = { navController.navigateUp() }
            )
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
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, bundle, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteDetailViewModel = hiltViewModel(it)
            bundle?.getLong(resultKey)?.let { id ->
                NoteDetailScreen(
                    state = rememberNoteDetailState(
                        note = remember { mutableStateOf(viewModel.getNoteDetail(id)) },
                        removeNote = { }
                    ),
                    onClickUpButton = navController::navigateUp,
                    onClickEditMenu = { navController.navigateSingleTopTo(route = "${NoteEditDestination.route}/$id")},
                )
            }
        }
    }
}