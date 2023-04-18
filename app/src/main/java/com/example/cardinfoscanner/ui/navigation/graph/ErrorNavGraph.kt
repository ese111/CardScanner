package com.example.cardinfoscanner.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.ui.navigation.destination.ErrorDestination
import com.example.cardinfoscanner.ui.navigation.destination.ResultDestination

@Stable
fun NavGraphBuilder.errorGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = ErrorDestination.routeWithArgs, arguments = ErrorDestination.arguments) {
            ErrorDestination.screen(navController, it.arguments)
        }
    }
}