package com.example.cardinfoscanner

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.errorRout
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.state.ResultState
import com.example.cardinfoscanner.state.rememberCameraScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.camera.CameraViewModel
import com.example.cardinfoscanner.ui.error.ErrorScreen
import com.example.cardinfoscanner.ui.permission.FeatureThatRequiresCameraPermission
import com.example.cardinfoscanner.ui.result.ResultScreen

object CameraDestination: Destination {
    override val route = cameraRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?, paddingValues: PaddingValues) -> Unit = { navController, _, paddingValues ->
        navController.currentBackStackEntry?.let {
            val viewModel: CameraViewModel = hiltViewModel(it)
            CameraPreViewScreen(
                state = rememberCameraScreenState(
                    cameraExecutor = viewModel.getCameraExecutor(),
                    imageCapture = viewModel.getImageCapture()
                ),
                navToResult = { state ->
                    Log.i("흥수", state)
                    if(state.isNotEmpty()) {
                        val str = state.replace("/", "+")
                        navController.navigateSingleTopTo("${ResultDestination.route}/$str")
                        return@CameraPreViewScreen
                    }
                    navController.navigateSingleTopTo("${ErrorDestination.route}/result")
                }
            )
        }
    }
}

object PermissionDestination: Destination {
    override val route = permissionRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?, paddingValues: PaddingValues) -> Unit = { navController, _, paddingValues ->
        navController.currentBackStackEntry?.let {
            FeatureThatRequiresCameraPermission(modifier = Modifier.padding(paddingValues), moveToNext = { navController.navigateSingleTopTo(cameraRoute) })
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
    override val screen: @Composable (NavHostController, Bundle?, PaddingValues) -> Unit = { navController, bundle, paddingValues ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(resultKey)?.let {
                val str = it.replace("+", "/")
                ResultScreen(state = ResultState(str), Modifier.padding(paddingValues))
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
    override val screen: @Composable (NavHostController, Bundle?, PaddingValues) -> Unit = { navController, bundle, paddingValues ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(errorKey)?.let {
                ErrorScreen(title = it, modifier = Modifier.padding(paddingValues))
            }
        }
    }
}