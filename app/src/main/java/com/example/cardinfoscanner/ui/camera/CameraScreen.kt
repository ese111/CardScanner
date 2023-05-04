package com.example.cardinfoscanner.ui.camera

import android.Manifest
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.cardinfoscanner.stateholder.camera.CameraScreenState
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.example.cardinfoscanner.ui.common.CardSnackBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import com.example.cardinfoscanner.ui.common.BasicTopAppBar
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CameraPreViewScreen(
    dialogState: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    cameraUtil: CameraUtil,
    navToResult: (String) -> Unit,
    navToPermission: () -> Unit,
    onUpButtonClick: () -> Unit,
    uiState: BaseUiState = rememberUiState(),
    value: MutableState<String> = remember{ mutableStateOf("") }
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
            BasicTopAppBar(title = "Card Scanner", backButtonVisible = true, onClickBackButton = onUpButtonClick)
        }
    ) { paddingValues ->
        if (dialogState.value) {
            NormalDialog(
                title = "아래 내용을 저장하시겠습니까?",
                phrase = value.value,
                confirmText = "확인",
                dismissText = "취소",
                onConfirm = { navToResult(value.value) },
                onDismiss = { dialogState.value = false }
            )
        }
        Timber.i("cameraUtil : ${cameraUtil.hashCode()}")
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreView(
                lifecycleOwner = uiState.lifecycleOwner,
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
            .height(60.dp)
            .width(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    ) {}
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