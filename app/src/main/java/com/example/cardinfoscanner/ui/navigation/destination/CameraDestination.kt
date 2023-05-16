package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.permission.rememberPermissionScreenState
import com.example.cardinfoscanner.ui.camera.CameraPreViewScreen
import com.example.cardinfoscanner.ui.permission.CameraPermissionBottomSheet
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalComposeUiApi::class)
object CameraDestination : Destination {
    override val route = cameraRoute
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit =
        { navController, _, _ ->
            navController.currentBackStackEntry?.let {
                val snackBarHostState = remember { SnackbarHostState() }
                val dialogState = remember { mutableStateOf(false) }
                val uiState: BaseUiState = rememberUiState()
                val value: MutableState<String> = remember { mutableStateOf("") }
                val cameraUtil = remember {
                    CameraUtil(uiState.context)
                        .addSuccessCallBack {
                            value.value = it
                            dialogState.value = true
                        }.addErrorCallBack {
                            uiState.scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                }
                var showPermissionBottomSheetState by remember {
                    mutableStateOf(false)
                }
                if (showPermissionBottomSheetState) {
                    CameraPermissionBottomSheet(
                        state = rememberPermissionScreenState(),
                        onDismissRequest = {
                            showPermissionBottomSheetState = false
                        },
                        moveToNext = {
                            navController.navigateSingleTopToGraph(
                                cameraRoute
                            )
                        }
                    )
                }
                CameraPreViewScreen(
                    cameraUtil = cameraUtil,
                    navToResult = { state ->
                        if (state.isNotEmpty()) {
                            val str = state.replace("/", "+")
                            navController.navigateSingleTopToGraph("${NoteEditDestination.route}/$str")
                            return@CameraPreViewScreen
                        }
                        uiState.scope.launch {
                            snackBarHostState.showSnackbar("인식된 정보가 없습니다.")
                        }
                    },
                    value = value,
                    navToPermission = {
                        showPermissionBottomSheetState = true
                    },
                    dialogState = dialogState,
                    snackbarHostState = snackBarHostState,
                    onUpButtonClick = navController::navigateUp
                )
            }
        }
}

object PermissionDestination : Destination {
    override val route = permissionRoute
    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit =
        { navController, _, _ ->
            navController.currentBackStackEntry?.let {
                CameraPermissionBottomSheet(
                    state = rememberPermissionScreenState(),
                    moveToNext = {
                        navController.navigateSingleTopToGraph(
                            cameraRoute
                        )
                    }
                )
            }
        }
}



