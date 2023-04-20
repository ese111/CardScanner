package com.example.cardinfoscanner.ui.camera

import android.Manifest
import android.view.View
import androidx.camera.core.ImageCapture
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.cardinfoscanner.ui.common.CardSnackBar
import com.example.cardinfoscanner.ui.common.NormalDialog
import com.example.cardinfoscanner.util.CallBack
import com.example.cardinfoscanner.util.CameraUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreViewScreen(
    navToResult: (String) -> Unit,
    navToPermission: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    if (!cameraPermissionState.status.isGranted) {
        navToPermission()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var value by remember { mutableStateOf("") }
    var dialogState by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraUtil = remember {
        CameraUtil(context = context)
            .setCallback(
                CallBack.OnBarcodeScanSuccessListener { str ->
                    value = str
                    dialogState = true
                }
            ).setCallback(
                CallBack.OnBarcodeScanErrorListener { str ->
                    scope.launch {
                        snackBarHostState.showSnackbar(str)
                    }
                }
            ).setCallback(
                CallBack.OnCaptureSuccessListener { str ->
                    value = str
                    dialogState = true
                }
            ).setCallback(
                CallBack.OnCaptureErrorListener { str ->
                    scope.launch {
                        snackBarHostState.showSnackbar(str)
                    }
                }
            )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            ) {
                CardSnackBar(snackbarData = it)
            }
        },
        topBar = {
            Text(text = "Card Scanner", modifier = Modifier.padding(18.dp))
        }
    ) { paddingValues ->
        if (dialogState) {
            NormalDialog(
                title = "아래 내용을 저장하시겠습니까?",
                phrase = value,
                confirmText = "확인",
                dismissText = "취소",
                onConfirm = {
                    navToResult(value)
                },
                onDismiss = {
                    dialogState = false
                }
            )
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreView(
                lifecycleOwner = lifecycleOwner,
                cameraUtil = cameraUtil
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            CameraButton {
                cameraUtil.takePicture()
            }
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
            cameraUtil.onBindScannerPreview(lifecycleOwner = lifecycleOwner)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
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
    val context = LocalContext.current
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