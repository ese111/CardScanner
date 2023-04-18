package com.example.cardinfoscanner.ui.camera

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.cardinfoscanner.util.takePicture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun CameraPreViewScreen(
    navToResult: (String) -> Unit,
//    cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor(),
//    imageCapture: ImageCapture = ImageCapture.Builder().build(),
//    scanner: BarcodeScanner = BarcodeScanning.getClient()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val scanner = remember {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            ).enableAllPotentialBarcodes()
            .build()
        BarcodeScanning.getClient(options)
    }
    Log.i("AppTest", "imageCapture : ${imageCapture.hashCode()} scanner ${scanner.hashCode()}")
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val executor = ContextCompat.getMainExecutor(ctx)

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
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp))
        CameraButton(
            context = context,
            cameraExecutor = cameraExecutor,
            imageCapture = imageCapture,
            onClick = navToResult
        )
    }

}

@Composable
private fun CameraButton(
    context: Context = LocalContext.current,
    imageCapture: ImageCapture = ImageCapture.Builder().build(),
    cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor(),
    onClick: (String) -> Unit
) {
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