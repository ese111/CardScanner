package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.stateholder.note.rememberNoteListState
import com.example.cardinfoscanner.stateholder.viewmodel.NoteListViewModel
import com.example.cardinfoscanner.ui.note.list.NoteListScreen

object NotesDestination: Destination {
    override val route = Destination.noteListRout
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: NoteListViewModel = hiltViewModel(it)
            NoteListScreen(
                state = rememberNoteListState(
                    noteList = viewModel.noteList
                ),
                onClickMenuButton = { navController.navigateSingleTopTo(Destination.cameraRoute) }
            )
        }
    }
}

object NoteDetailDestination: Destination {
    override val route = Destination.noteListRout
    private const val resultKey = "result"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.StringType }
    )
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, bundle ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(resultKey)?.let {
                val str = it.replace("+", "/")
            }
        }
    }
}