package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.ui.navigation.destination.SettingDestination

@Stable
fun NavGraphBuilder.settingGraph(
    navController: NavHostController,
    startDestination: String,
    route: String,
    mainViewModel: MainViewModel
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = Destination.settingRout) {
            SettingDestination.screen(navController, it.arguments, mainViewModel)
        }
    }
}