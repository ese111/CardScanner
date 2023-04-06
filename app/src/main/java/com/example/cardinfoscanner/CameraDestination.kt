package com.example.cardinfoscanner

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute

object CameraDestination: Destination {
    override val route = cameraRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
//            CameraPreViewScreen()
        }
    }
}

object PermissionDestination: Destination {
    override val route = permissionRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            FeatureThatRequiresCameraPermission(navController)
        }
    }
}