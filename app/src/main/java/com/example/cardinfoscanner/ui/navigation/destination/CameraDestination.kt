package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.navigateClearTo
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.stateholder.camera.rememberCameraScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.permission.FeatureThatRequiresCameraPermission
import com.example.cardinfoscanner.util.CameraUtil
import kotlinx.coroutines.launch
import timber.log.Timber


object CameraDestination : Destination {
    override val route = cameraRoute
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val snackBarHostState = remember { SnackbarHostState() }
            val dialogState = remember { mutableStateOf(false) }
            val cameraState = rememberCameraScreenState()
            val cameraUtil = remember {
                CameraUtil(cameraState.uiState.context)
                    .addSuccessCallBack {
                        cameraState.value.value = it
                        dialogState.value = true
                    }.addErrorCallBack {
                        cameraState.uiState.scope.launch {
                            snackBarHostState.showSnackbar(it)
                        }
                    }
            }
            Timber.tag("AppTest").i(cameraUtil.hashCode().toString())
            CameraPreViewScreen(
                cameraUtil = cameraUtil,
                navToResult = { state ->
                    Timber.tag("AppTest").i(state)
                    if (state.isNotEmpty()) {
                        val str = state.replace("/", "+")
                        navController.navigateSingleTopTo("${NoteEditDestination.route}/$str")
                        return@CameraPreViewScreen
                    }
                    cameraState.uiState.scope.launch {
                        snackBarHostState.showSnackbar("인식된 정보가 없습니다.")
                    }
                },
                navToPermission = { navController.navigateClearTo(permissionRoute) },
                takePicture = cameraUtil::takePicture,
                dialogState = dialogState,
                cameraState = cameraState,
                snackbarHostState = snackBarHostState,
                onUpButtonClick = navController::navigateUp
            )
        }
    }
}

object PermissionDestination : Destination {
    override val route = permissionRoute
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, _, _->
        navController.currentBackStackEntry?.let {
            FeatureThatRequiresCameraPermission(moveToNext = {
                navController.navigateClearTo(
                    cameraRoute
                )
            })
        }
    }
}



