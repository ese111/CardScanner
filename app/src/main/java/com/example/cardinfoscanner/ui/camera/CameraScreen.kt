package com.example.cardinfoscanner.ui.camera

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.cardinfoscanner.state.CameraScreenState
import com.example.cardinfoscanner.util.recognizeText
import com.example.cardinfoscanner.util.scanBarcode
import com.example.cardinfoscanner.util.takePicture
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun CameraPreViewScreen(
    state: CameraScreenState,
    navToResult: (String) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

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
                            scanBarcode(image).addOnSuccessListener { list ->
                                list.forEach {  barcode ->
                                    when (barcode.valueType) {
                                        Barcode.TYPE_WIFI -> {
                                            val ssid = barcode.wifi!!.ssid
                                            val password = barcode.wifi!!.password
                                            val type = barcode.wifi!!.encryptionType
                                            Log.i("흥수", "$ssid")
                                            Log.i("흥수", "$password")
                                            Log.i("흥수", "$type")
                                        }
                                        Barcode.TYPE_URL -> {
                                            val title = barcode.url!!.title
                                            val url = barcode.url!!.url
                                            Log.i("흥수", "title ${title}")
                                            Log.i("흥수", "url $url")
                                            navToResult(url.toString())
                                        }
                                    }
                                }
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
                        state.imageCapture,
                        analyzer
                    )
                }, executor)
                previewView
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                takePicture(
                    context = context,
                    imageCapture = state.imageCapture,
                    executorService = state.cameraExecutor,
                    navToResult = navToResult
                )
            },
            modifier = Modifier
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}