package com.example.cardinfoscanner.ui.camera

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.camera.CameraScreenState
import com.example.cardinfoscanner.stateholder.camera.rememberCameraScreenState
import com.example.cardinfoscanner.stateholder.permission.PermissionScreenState
import com.example.cardinfoscanner.stateholder.permission.rememberPermissionScreenState
import com.example.cardinfoscanner.ui.common.CardSnackBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import com.example.cardinfoscanner.ui.common.BasicTopAppBar
import com.example.cardinfoscanner.ui.permission.CameraPermissionBottomSheet
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraPreViewScreen(
    state: CameraScreenState = rememberCameraScreenState(),
    cameraPermissionState: PermissionScreenState = rememberPermissionScreenState(),
    cameraUtil: CameraUtil,
    navToResult: (String) -> Unit = {},
    navToPermission: () -> Unit = {},
    onUpButtonClick: () -> Unit = {},
    onBottomSheetDismissRequest: () -> Unit = {},
    moveToCamera: () -> Unit = {}
) {
    if (!cameraPermissionState.permissionState.status.isGranted) {
        navToPermission()
    }
    if (state.showPermissionBottomSheetState.value) {
        CameraPermissionBottomSheet(
            state = cameraPermissionState,
            onDismissRequest = onBottomSheetDismissRequest,
            moveToNext = moveToCamera
        )
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = state.snackBarHostState
            ) {
                CardSnackBar(snackbarData = it)
            }
        },
        topBar = {
            BasicTopAppBar(title = "Card Scanner", backButtonVisible = true, onClickBackButton = onUpButtonClick)
        }
    ) { paddingValues ->
        if (state.dialogState.value) {
            NormalDialog(
                title = "아래 내용을 저장하시겠습니까?",
                phrase = state.value.value,
                confirmText = "확인",
                dismissText = "취소",
                onConfirm = { navToResult(state.value.value) },
                onDismiss = { state.dialogState.value = false }
            )
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreView(
                lifecycleOwner = state.uiState.lifecycleOwner,
                cameraUtil = cameraUtil
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
            CameraButton(
                onClick = cameraUtil::takePicture
            )
        }
    }
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun CameraPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { ctx ->
                View(ctx)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .height(700.dp),
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .background(Color.Red)
        )
        CameraButton {}
        Spacer(modifier = Modifier.weight(1f))
    }
}