package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import timber.log.Timber

object NotesDestination: Destination {
    override val route = Destination.noteListRout
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, _, mainViewModel ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            val noteListState = viewModel.noteList.collectAsStateWithLifecycle()
            val event = mainViewModel?.subscribe<Note>()?.collectAsStateWithLifecycle(initialValue = Note(-1,"","",""))
            Timber.tag("AppTest").d("event : ${event?.value}")
            if(event?.value?.date?.isNotEmpty() == true) {
                viewModel.setNotesList(event.value)
            }
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
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, bundle, mainViewModel->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            bundle?.getString(resultKey)?.let { scanText ->
                NoteEditScreen(
                    state = rememberNoteState(
                        content = mutableStateOf(scanText.replace("+", "/")),
                        setNote = { note -> viewModel.setNotesList(note) }
                    ),
                    onClickSave = {
                        navController.navigateSingleTopTo(NotesDestination.route, true)
                    },
                    onClickCancel = { navController.navigateUp() }
                )
            }
        }
    }
}