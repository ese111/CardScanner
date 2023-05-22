package com.example.cardinfoscanner.ui.navigation.navhost

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.app.rememberAppState
import com.example.cardinfoscanner.ui.navigation.graph.cameraGraph
import com.example.cardinfoscanner.ui.navigation.graph.noteGraph
import com.example.cardinfoscanner.ui.navigation.graph.settingGraph

@Composable
fun ScannerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appState: AppState = rememberAppState()
) {
    NavHost(
        navController = navController,
        startDestination = Destination.noteListRout,
        modifier = modifier
    ) {
        noteGraph(
            navController = navController,
            appState = appState
        )
        cameraGraph(
            navController = navController,
            appState = appState
        )
        settingGraph(
            navController = navController,
            appState = appState
        )
    }
}