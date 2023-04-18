package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.errorRout
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.state.ResultState
import com.example.cardinfoscanner.state.rememberCameraScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.camera.CameraViewModel
import com.example.cardinfoscanner.ui.error.ErrorScreen
import com.example.cardinfoscanner.ui.permission.FeatureThatRequiresCameraPermission
import com.example.cardinfoscanner.ui.result.ResultScreen
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import java.util.concurrent.Executors


object CameraDestination: Destination {
    override val route = cameraRoute
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            val viewModel: CameraViewModel = hiltViewModel(it)
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE
                ).enableAllPotentialBarcodes()
                .build()
            CameraPreViewScreen(
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
    override val screen: @Composable (NavHostController, Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            FeatureThatRequiresCameraPermission(moveToNext = { navController.navigateSingleTopTo(cameraRoute) })
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
                ResultScreen(state = ResultState(str))
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