package com.example.cardinfoscanner.ui.camera

import android.Manifest
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.cardinfoscanner.stateholder.camera.CameraScreenState
import com.example.cardinfoscanner.ui.common.CardSnackBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import com.example.cardinfoscanner.ui.common.TopAppBar
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreViewScreen(
    dialogState: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState,
    cameraState: CameraScreenState,
    cameraUtil: CameraUtil,
    navToResult: (String) -> Unit,
    navToPermission: () -> Unit,
    takePicture: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    if (!cameraPermissionState.status.isGranted) {
        navToPermission()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                CardSnackBar(snackbarData = it)
            }
        },
        topBar = {
            TopAppBar(title = "Card Scanner")
        }
    ) { paddingValues ->
        if (dialogState.value) {
            NormalDialog(
                title = "아래 내용을 저장하시겠습니까?",
                phrase = cameraState.value.value,
                confirmText = "확인",
                dismissText = "취소",
                onConfirm = {
                    navToResult(cameraState.value.value)
                },
                onDismiss = {
                    dialogState.value = false
                }
            )
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreView(
                lifecycleOwner = cameraState.uiState.lifecycleOwner,
                cameraUtil = cameraUtil
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            CameraButton(
                onClick = takePicture
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
private fun CameraPreView(
    lifecycleOwner: LifecycleOwner,
    cameraUtil: CameraUtil
) {
    AndroidView(
        factory = {
            cameraUtil.onBindScannerPreview(
                lifecycleOwner = lifecycleOwner
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    )
}

@Composable
private fun CameraButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(80.dp)
            .width(80.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
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