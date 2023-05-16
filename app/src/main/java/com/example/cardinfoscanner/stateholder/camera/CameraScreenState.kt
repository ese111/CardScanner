package com.example.cardinfoscanner.stateholder.camera

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.stateholder.permission.PermissionScreenState
import com.example.cardinfoscanner.stateholder.permission.rememberPermissionScreenState
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@Stable
class CameraScreenState(
    val uiState: BaseUiState,
    var value: MutableState<String>,
    val title: MutableState<String>,
    val snackBarHostState: SnackbarHostState,
    val dialogState: MutableState<Boolean>,
    val showPermissionBottomSheetState: MutableState<Boolean>,
) {
    val onSuccessScanText: (String) -> Unit = {
        value.value = it
        dialogState.value = true
    }
    val onErrorScanText: (String) -> Unit = {
        uiState.scope.launch {
            snackBarHostState.showSnackbar(it)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberCameraScreenState(
    uiState: BaseUiState = rememberUiState(),
    value: MutableState<String> = remember{ mutableStateOf("") },
    title: MutableState<String> = remember { mutableStateOf("") },
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    dialogState: MutableState<Boolean> = remember { mutableStateOf(false) },
    showPermissionBottomSheetState: MutableState<Boolean> = remember { mutableStateOf(false) }
): CameraScreenState = remember(
    uiState,
    value,
    title,
    snackBarHostState,
    dialogState,
    showPermissionBottomSheetState
) {
    CameraScreenState(
        uiState = uiState,
        value = value,
        title = title,
        snackBarHostState = snackBarHostState,
        dialogState = dialogState,
        showPermissionBottomSheetState = showPermissionBottomSheetState
    )
}