package com.example.cardinfoscanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.ResultDestination.routeWithArgs

@Composable
fun CameraGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(route = permissionRoute) {
            PermissionDestination.screen(navController, it.arguments)
        }
        composable(route = cameraRoute) {
            CameraDestination.screen(navController, it.arguments)
        }
        composable(route = routeWithArgs, arguments = ResultDestination.arguments) {
            ResultDestination.screen(navController, it.arguments)
        }
    }
}
