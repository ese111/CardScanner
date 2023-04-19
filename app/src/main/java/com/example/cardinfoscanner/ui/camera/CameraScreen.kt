package com.example.cardinfoscanner.ui.camera

import android.Manifest
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.cardinfoscanner.state.ResultState
import com.example.cardinfoscanner.ui.common.CardSnackBar
import com.example.cardinfoscanner.util.takePicture
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreViewScreen(
    navToResult: (String) -> Unit,
    navToPermission: () -> Unit,
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    if (!cameraPermissionState.status.isGranted) {
        navToPermission()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }


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
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreView(
                imageCapture = imageCapture,
                navToResult = navToResult,
                snackBarHostState = snackBarHostState,
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp))
            CameraButton(
                cameraExecutor = cameraExecutor,
                imageCapture = imageCapture,
                onClick = navToResult
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
private fun CameraPreView(
    imageCapture: ImageCapture,
    navToResult: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scanner = remember {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            ).enableAllPotentialBarcodes()
            .build()
        BarcodeScanning.getClient(options)
    }
    val scope = rememberCoroutineScope()
    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                val myAnalyzer = ImageAnalysis.Analyzer { imageProxy ->
                    val mediaImage = imageProxy.image
                    mediaImage?.let {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image).addOnSuccessListener { list ->
                            list.forEach {  barcode ->
                                when (barcode.valueType) {
                                    Barcode.TYPE_WIFI -> {
                                        val ssid = barcode.wifi!!.ssid
                                        val password = barcode.wifi!!.password
                                        val type = barcode.wifi!!.encryptionType
                                        Log.i("CardScanner", "$ssid")
                                        Log.i("CardScanner", "$password")
                                        Log.i("CardScanner", "$type")

                                    }
                                    Barcode.TYPE_URL -> {
                                        val title = barcode.url!!.title
                                        val url = barcode.url!!.url
                                        Log.i("CardScanner", "title $title")
                                        Log.i("CardScanner", "url $url")
                                        navToResult(url.toString())
                                    }
                                }
                            }
                        }.addOnCompleteListener {
                            imageProxy.close()
                            mediaImage.close()
                        }.addOnFailureListener {
                            scope.launch {
                                snackBarHostState.showSnackbar("${it.message}")
                            }
                        }
                    }
                }

                val analyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build().apply {
                        setAnalyzer(executor, myAnalyzer)
                    }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture,
                    analyzer
                )
            }, executor)
            previewView
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
    )
}

@Composable
private fun CameraButton(
    imageCapture: ImageCapture = ImageCapture.Builder().build(),
    cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor(),
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    Button(
        onClick = {
            takePicture(
                context = context,
                imageCapture = imageCapture,
                executorService = cameraExecutor,
                navToResult = onClick
            )
        },
        modifier = Modifier
            .height(80.dp)
            .width(80.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = null, modifier = Modifier.fillMaxSize())
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
        Spacer(modifier = Modifier
            .weight(1f)
            .background(Color.Red))
        CameraButton {}
        Spacer(modifier = Modifier.weight(1f))
    }
}