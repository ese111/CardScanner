package com.example.cardinfoscanner.state

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Stable
data class CameraScreenState(
    val cameraExecutor: ExecutorService,
    val imageCapture: ImageCapture,
    val scanner: BarcodeScanner
)

@Composable
fun rememberCameraScreenState(
    cameraExecutor: ExecutorService,
    imageCapture: ImageCapture,
    scanner: BarcodeScanner
): CameraScreenState = remember(cameraExecutor, imageCapture) {
    CameraScreenState(
        cameraExecutor = cameraExecutor,
        imageCapture = imageCapture,
        scanner = scanner
    )
}