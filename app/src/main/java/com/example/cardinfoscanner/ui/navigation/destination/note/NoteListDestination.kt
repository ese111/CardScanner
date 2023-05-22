package com.example.cardinfoscanner.ui.navigation.destination.note

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.list.NoteListScreen
import timber.log.Timber

object NoteListDestination: Destination {
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
                moveToCamera = { navController.navigateSingleTopToGraph(Destination.cameraRoute) },
                moveToEdit = { navController.navigateSingleTopToGraph(Destination.noteWriteRout) },
                onClickNote = { id -> navController.navigateSingleTopToGraph("${NoteDetailDestination.route}/$id") },
                onCancelRemove = viewModel::cancelRemove,
                onRemoveNote = viewModel::removeNote
            )
        }
    }
}