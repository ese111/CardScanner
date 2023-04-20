package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.errorRout
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.navigateClearTo
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.error.ErrorScreen
import com.example.cardinfoscanner.ui.permission.FeatureThatRequiresCameraPermission
import com.example.cardinfoscanner.ui.result.ResultScreen


object CameraDestination: Destination {
    override val route = cameraRoute
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            CameraPreViewScreen(
                navToResult = { state ->
                    Log.i("흥수", state)
                    if(state.isNotEmpty()) {
                        val str = state.replace("/", "+")
                        navController.navigateSingleTopTo("${ResultDestination.route}/$str")
                        return@CameraPreViewScreen
                    }
                    navController.navigateSingleTopTo("${ErrorDestination.route}/result")
                },
                navToPermission = { navController.navigateClearTo(permissionRoute) }
            )
        }
    }
}

object PermissionDestination: Destination {
    override val route = permissionRoute
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            FeatureThatRequiresCameraPermission(moveToNext = { navController.navigateClearTo(cameraRoute) })
        }
    }
}

object ResultDestination: Destination {
    override val route = resultRout
    private const val resultKey = "result"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.StringType }
    )
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, bundle ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(resultKey)?.let {
                val str = it.replace("+", "/")
                ResultScreen(text = str)
            }
        }
    }
}

object ErrorDestination: Destination {
    override val route = errorRout
    private const val errorKey = "title"
    val routeWithArgs = "$route/{$errorKey}"
    val arguments = listOf(
        navArgument(errorKey) { type = NavType.StringType }
    )
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, bundle ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(errorKey)?.let {
                ErrorScreen(title = it)
            }
        }
    }
}