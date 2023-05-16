package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.permission.rememberPermissionScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.permission.CameraPermissionBottomSheet
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch
import com.example.cardinfoscanner.stateholder.camera.rememberCameraScreenState
import com.example.cardinfoscanner.util.CameraUtil
import timber.log.Timber

object CameraDestination : Destination {
    override val route = cameraRoute
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit =
        { navController, _, _ ->
            navController.currentBackStackEntry?.let {
                val state = rememberCameraScreenState()
                val cameraUtil = remember {
                    CameraUtil(state.uiState.context)
                        .addSuccessCallBack { str ->
                            Timber.i("state : $str}")
                            state.onSuccessScanText(str)
                        }.addErrorCallBack { str ->
                            state.onErrorScanText(str)
                        }
                }
                Timber.i("state : ${state.hashCode()} ${cameraUtil.hashCode()}")
                Timber.i("qusrud : !!! CameraPreViewScreen")
                CameraPreViewScreen(
                    state = state,
                    cameraUtil = cameraUtil,
                    navToResult = { scanText ->
                        if (scanText.isNotEmpty()) {
                            val str = scanText.replace("/", "+")
                            navController.navigateSingleTopToGraph("${NoteEditDestination.route}/$str")
                            return@CameraPreViewScreen
                        }
                        state.uiState.scope.launch {
                            state.dialogState.value = false
                            state.snackBarHostState.showSnackbar("인식된 정보가 없습니다.")
                        }
                    },
                    navToPermission = {
                        state.showPermissionBottomSheetState.value = true
                    },
                    onUpButtonClick = navController::navigateUp,
                    moveToCamera = {
                        navController.navigateSingleTopToGraph(cameraRoute)
                    },
                    onBottomSheetDismissRequest = state.onDismissBottomSheet
                )
            }
        }
}




