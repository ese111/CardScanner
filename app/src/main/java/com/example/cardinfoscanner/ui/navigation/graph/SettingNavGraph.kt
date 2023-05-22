package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.ui.navigation.destination.setting.SettingDestination

@Stable
fun NavGraphBuilder.settingGraph(
    navController: NavHostController,
    appState: AppState
) {
    composable(route = Destination.settingRout) {
        SettingDestination.screen(navController, it.arguments, appState)
    }
}