package com.example.cardinfoscanner

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.state.ResultState
import com.example.cardinfoscanner.state.rememberCameraScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.camera.CameraViewModel
import com.example.cardinfoscanner.ui.permission.FeatureThatRequiresCameraPermission
import com.example.cardinfoscanner.ui.result.ResultScreen

object CameraDestination: Destination {
    override val route = cameraRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: CameraViewModel = hiltViewModel(it)
            CameraPreViewScreen(
                state = rememberCameraScreenState(
                    cameraExecutor = viewModel.getCameraExecutor(),
                    imageCapture = viewModel.getImageCapture()
                ),
                navToResult = { state ->
                    val str = state.replace("/", " ")
                    navController.navigateSingleTopTo("${ResultDestination.route}/$str")
                }
            )
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

object ResultDestination: Destination {
    override val route = resultRout
    private const val resultKey = "result"
    val routeWithArgs = "$route/{$resultKey}"
    val arguments = listOf(
        navArgument(resultKey) { type = NavType.StringType }
    )
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, bundle ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(resultKey)?.let {
                ResultScreen(state = ResultState(it))
            }
        }
    }
}