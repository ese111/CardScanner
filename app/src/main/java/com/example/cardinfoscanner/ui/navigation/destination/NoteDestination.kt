package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.stateholder.note.Note
import com.example.cardinfoscanner.stateholder.note.rememberNoteListState
import com.example.cardinfoscanner.stateholder.note.rememberNoteState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.detail.NoteEditScreen
import com.example.cardinfoscanner.ui.note.list.NoteListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

object NotesDestination: Destination {
    override val route = Destination.noteListRout
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            val noteListState by viewModel.noteList.collectAsStateWithLifecycle()

            NoteListScreen(
                state = rememberNoteListState(
                    noteList = noteListState,
                    setData = { note -> viewModel.setNotesList(note) }
                ),
                onClickMenuButton = { navController.navigateSingleTopTo(Destination.cameraRoute) }
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
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, bundle ->
        navController.currentBackStackEntry?.let {
            val mainViewModel: MainViewModel by hiltViewModel(it)
            bundle?.getString(resultKey)?.let {
                NoteEditScreen(
                    state = rememberNoteState(
                        content = mutableStateOf(it.replace("+", "/"))
                    ),
                    onClickSave = { title, content ->
                        mainViewModel.putEvent(Note(title = title, content = content, date = System.currentTimeMillis()))
                    },
                    onClickCancel = { navController.navigateUp() }
                )
            }
        }
    }
}