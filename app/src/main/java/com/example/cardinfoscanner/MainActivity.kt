package com.example.cardinfoscanner

import android.content.Context
import android.media.MediaActionSound
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.ui.theme.CardInfoScannerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardInfoScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
                    val file = remember {
                        File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
                    }
                    val imageCapture = remember { ImageCapture.Builder().build() }

                    CameraPreViewScreen(
                        imageCapture = imageCapture,
                        file = file,
                        executorService = cameraExecutor
                    )
                }
            }
        }
    }
}

@Composable
fun CardScannerApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { Text(text = "Card Scanner", modifier = Modifier.padding(18.dp)) }
    ) { paddingValues ->
        CameraGraph(
            navController = navController,
            startDestination = permissionRoute,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun CameraPreViewScreen(
    imageCapture: ImageCapture,
    file: File,
    executorService: ExecutorService
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    Column(
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

//                    val analyzer = ImageAnalysis.Builder()
//                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                        .build()
//                        .apply {
//                            setAnalyzer(executor, CardScannerImageAnalyzer())
//                        }

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageCapture,
                        preview
                    )
                }, executor)
                previewView
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )

        Button(
            onClick = {
                takePicture(
                    context = context,
                    imageCapture = imageCapture,
                    file = file,
                    executorService = executorService
                )
            },
            modifier = Modifier.weight(0.9f)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresCameraPermission(
    navController: NavHostController
) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    if (cameraPermissionState.hasPermission) {
        Text("Camera permission Granted")

        navController.navigateSingleTopTo(cameraRoute)
    } else {
        Column {
            val textToShow = if (cameraPermissionState.shouldShowRationale) {
                "The camera is important for this app. Please grant the permission."
            } else {
                "Camera permission required for this feature to be available. Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
private class CardScannerImageAnalyzer : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            Log.i("흥수", "image send")
            recognizeText(image).addOnCompleteListener {
                mediaImage.close()
                imageProxy.close()
            }
        }
    }
}

private fun recognizeText(image: InputImage): Task<Text> {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val koreanRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    Log.i("흥수", "rr")
    return koreanRecognizer.process(image)
        .addOnSuccessListener { visionText ->
            for (block in visionText.textBlocks) {
                val boundingBox = block.boundingBox
                val cornerPoints = block.cornerPoints
                val text = block.text
                Log.i("흥수 ko 1", text)
                processTextBlock(visionText)
            }
        }.addOnFailureListener { e ->
            Log.e("$e", "koreanRecognizer 실패 ${e.message}")
        }
}



private fun processTextBlock(result: Text) {
    // [START mlkit_process_text_block]
    val resultText = result.text
    Log.i("흥수2", resultText)
    for (block in result.textBlocks) {
        val blockText = block.text
        val blockCornerPoints = block.cornerPoints
        val blockFrame = block.boundingBox
        Log.i("흥수3", blockText)
        for (line in block.lines) {
            val lineText = line.text
            val lineCornerPoints = line.cornerPoints
            val lineFrame = line.boundingBox
            Log.i("흥수4", lineText)
            for (element in line.elements) {
                val elementText = element.text
                val elementCornerPoints = element.cornerPoints
                val elementFrame = element.boundingBox
                Log.i("흥수5", elementText)
            }
        }
    }
    // [END mlkit_process_text_block]
}

private fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    file: File,
    executorService: ExecutorService
) {

    MediaActionSound().play(MediaActionSound.SHUTTER_CLICK) // 셔터 소리
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
    imageCapture.takePicture(outputFileOptions, executorService,
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(error: ImageCaptureException) {
                // insert your code here.
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let { recognizeText(InputImage.fromFilePath(context, it)) }
            }
        })

}

