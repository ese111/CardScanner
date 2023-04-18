package com.example.cardinfoscanner

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.ui.navigation.destination.CameraDestination
import com.example.cardinfoscanner.ui.navigation.destination.ErrorDestination
import com.example.cardinfoscanner.ui.navigation.destination.PermissionDestination
import com.example.cardinfoscanner.ui.navigation.destination.ResultDestination
import com.example.cardinfoscanner.ui.navigation.destination.ResultDestination.routeWithArgs

@Composable
fun CameraGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier.padding(paddingValues)) {
        composable(route = permissionRoute) {
            PermissionDestination.screen(navController, it.arguments)
        }
        composable(route = cameraRoute) {
            CameraDestination.screen(navController, it.arguments)
        }
        composable(route = routeWithArgs, arguments = ResultDestination.arguments) {
            ResultDestination.screen(navController, it.arguments)
        }
        composable(route = ErrorDestination.routeWithArgs, arguments = ErrorDestination.arguments) {
            ErrorDestination.screen(navController, it.arguments)
        }
    }
}

@Stable
fun NavGraphBuilder.cameraGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = permissionRoute) {
            PermissionDestination.screen(navController, it.arguments)
        }
        composable(route = cameraRoute) {
            CameraDestination.screen(navController, it.arguments)
        }
    }
}

