package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.ui.navigation.destination.CameraDestination
import com.example.cardinfoscanner.util.CameraUtil
import javax.inject.Inject

@Stable
fun NavGraphBuilder.cameraGraph(
    navController: NavHostController,
    appState: AppState
) {
    composable(route = cameraRoute) {
        CameraDestination.screen(navController, it.arguments, appState)
    }
}

