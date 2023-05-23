package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteDetailDestination
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteEditDestination
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteListDestination
import com.example.cardinfoscanner.ui.navigation.destination.note.NoteWriteDestination

@Stable
fun NavGraphBuilder.noteGraph(
    navController: NavHostController,
    appState: AppState
) {
    composable(route = Destination.noteListRout) {
        NoteListDestination.screen(navController, it.arguments, appState)
    }
    composable(route = NoteEditDestination.routeWithArgs, arguments = NoteEditDestination.arguments) {
        NoteEditDestination.screen(navController, it.arguments, appState)
    }
    composable(route = NoteDetailDestination.routeWithArgs, arguments = NoteDetailDestination.arguments) {
        NoteDetailDestination.screen(navController, it.arguments, appState)
    }
    composable(route = NoteWriteDestination.route) {
        NoteWriteDestination.screen(navController, it.arguments, appState)
    }
}
