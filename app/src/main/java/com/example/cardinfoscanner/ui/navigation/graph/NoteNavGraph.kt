package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.ui.navigation.destination.NoteDetailDestination
import com.example.cardinfoscanner.ui.navigation.destination.NoteEditDestination
import com.example.cardinfoscanner.ui.navigation.destination.NotesDestination
import com.example.cardinfoscanner.ui.note.detail.NoteDetailScreen

@Stable
fun NavGraphBuilder.noteGraph(
    navController: NavHostController,
    appState: AppState
) {
    composable(route = Destination.noteListRout) {
        NotesDestination.screen(navController, it.arguments, appState)
    }
    composable(route = NoteEditDestination.routeWithArgs, arguments = NoteEditDestination.arguments) {
        NoteEditDestination.screen(navController, it.arguments, appState)
    }
    composable(route = NoteDetailDestination.routeWithArgs, arguments = NoteDetailDestination.arguments) {
        NoteDetailDestination.screen(navController, it.arguments, appState)
    }
}
