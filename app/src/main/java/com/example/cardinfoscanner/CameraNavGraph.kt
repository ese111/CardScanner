package com.example.cardinfoscanner

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier.padding(paddingValues)) {
        composable(route = permissionRoute) {
            PermissionDestination.screen(navController, it.arguments, paddingValues)
        }
        composable(route = cameraRoute) {
            CameraDestination.screen(navController, it.arguments, paddingValues)
        }
        composable(route = routeWithArgs, arguments = ResultDestination.arguments) {
            ResultDestination.screen(navController, it.arguments, paddingValues)
        }
        composable(route = ErrorDestination.routeWithArgs, arguments = ErrorDestination.arguments) {
            ErrorDestination.screen(navController, it.arguments, paddingValues)
        }
    }
}
