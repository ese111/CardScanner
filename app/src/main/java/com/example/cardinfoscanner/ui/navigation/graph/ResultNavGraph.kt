package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.ui.navigation.destination.CameraDestination
import com.example.cardinfoscanner.ui.navigation.destination.PermissionDestination
import com.example.cardinfoscanner.ui.navigation.destination.ResultDestination

@Stable
fun NavGraphBuilder.resultGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = ResultDestination.routeWithArgs, arguments = ResultDestination.arguments) {
            ResultDestination.screen(navController, it.arguments)
        }
    }
}
