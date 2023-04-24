package com.example.cardinfoscanner.util

import android.content.Context
import android.media.MediaActionSound
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

class CameraUtil(
    private val context: Context,
) {
    enum class CallBackType {
        ON_SUCCESS, ON_FAIL
    }
    private val imageCapture = ImageCapture.Builder().build()
    private val executor = ContextCompat.getMainExecutor(context)
    private val callBacks = hashMapOf<CallBackType, (String) -> Unit>()
    private fun Context.getOutputDirectory() =
        File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

    internal fun addSuccessCallBack(callBack: (String) -> Unit): CameraUtil {
        callBacks[CallBackType.ON_SUCCESS] = callBack
        return this
    }

    internal fun addErrorCallBack(callBack: (String) -> Unit): CameraUtil {
        callBacks[CallBackType.ON_FAIL] = callBack
        return this
    }
    internal fun takePicture() {
        MediaActionSound().play(MediaActionSound.SHUTTER_CLICK) // 셔터 소리
        val outputDirectory = context.getOutputDirectory()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(outputDirectory).build()
        imageCapture.takePicture(outputFileOptions, executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Log.i("흥수", "sad ${error.message}")
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.i("흥수", "sa ${outputFileResults.savedUri.toString()}")
                    outputFileResults.savedUri?.let {
                        Log.i("흥수", it.toString())
                        recognizeText(InputImage.fromFilePath(context, it))
                            .addOnSuccessListener { task ->
                                Log.i("흥수", task.text)
                                callBacks[CallBackType.ON_SUCCESS]?.invoke(task.text)
                            }.addOnFailureListener { e ->
                                Log.i("흥수", e.message.toString())
                                callBacks[CallBackType.ON_FAIL]?.invoke(e.message.toString())
                            }
                    }
                }
            })
    }

    private fun getScanner(): BarcodeScanner {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            ).enableAllPotentialBarcodes()
            .build()
        return BarcodeScanning.getClient(options)
    }

    private fun getPreview(surfaceProvider: Preview.SurfaceProvider) =
        Preview.Builder().build().apply {
            setSurfaceProvider(surfaceProvider)
        }

    private fun getCameraSelector() = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    private fun getImageAnalyzer(): ImageAnalysis.Analyzer {
        val scanner = getScanner()
        return ImageAnalysis.Analyzer { imageProxy ->
            val mediaImage = imageProxy.image
            mediaImage?.let {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )
                scanner.process(image).addOnSuccessListener { list ->
                    list.forEach { barcode ->
                        when (barcode.valueType) {
                            Barcode.TYPE_WIFI -> {
                                val ssid = barcode.wifi!!.ssid
                                val password = barcode.wifi!!.password
                                val type = barcode.wifi!!.encryptionType
                                Log.i("CardScanner", "$ssid")
                                Log.i("CardScanner", "$password")
                                Log.i("CardScanner", "$type")
                                barcode.wifi?.let { callBacks[CallBackType.ON_SUCCESS]?.invoke(it.toString()) }
                            }

                            Barcode.TYPE_URL -> {
                                val title = barcode.url!!.title
                                val url = barcode.url!!.url
                                Log.i("CardScanner", "title $title")
                                Log.i("CardScanner", "url $url")
                                url?.let { callBacks[CallBackType.ON_SUCCESS]?.invoke(it) }
                            }
                        }
                    }
                }.addOnCompleteListener {
                    imageProxy.close()
                    mediaImage.close()
                }.addOnFailureListener {
                    callBacks[CallBackType.ON_FAIL]?.invoke("바코드 스캔에 실패하였습니다.")
                }
            }
        }
    }

    private fun getAnalysis() = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().apply {
            setAnalyzer(
                executor,
                getImageAnalyzer()
            )
        }

    internal fun onBindScannerPreview(lifecycleOwner: LifecycleOwner): PreviewView {
        val previewView = PreviewView(context)
        val executor = ContextCompat.getMainExecutor(context)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = getPreview(previewView.surfaceProvider)
            val cameraSelector = getCameraSelector()
            val analysis = getAnalysis()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                analysis
            )
        }, executor)
        return previewView
    }
}
