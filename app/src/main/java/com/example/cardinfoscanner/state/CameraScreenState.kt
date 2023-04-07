package com.example.cardinfoscanner.state

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Stable
data class CameraScreenState(
    val cameraExecutor: ExecutorService,
    val imageCapture: ImageCapture
)

@Composable
fun rememberCameraScreenState(
    cameraExecutor: ExecutorService,
    imageCapture: ImageCapture
): CameraScreenState = remember(cameraExecutor, imageCapture) {
    CameraScreenState(
        cameraExecutor = cameraExecutor,
        imageCapture = imageCapture
    )
}