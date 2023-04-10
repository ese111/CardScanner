package com.example.cardinfoscanner.util

import android.content.Context
import android.media.MediaActionSound
import android.os.Build
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.cardinfoscanner.getOutputDirectory
import com.example.cardinfoscanner.state.ResultState
import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.nio.file.Files.createFile
import java.util.concurrent.ExecutorService

fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    executorService: ExecutorService,
    navToResult: (String) -> Unit
) : String {
    MediaActionSound().play(MediaActionSound.SHUTTER_CLICK) // 셔터 소리
    val outputDirectory = context.getOutputDirectory()
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(outputDirectory).build()
    var value = ""
    imageCapture.takePicture(outputFileOptions, executorService,
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(error: ImageCaptureException) {
                value = "fail"
            }
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let {
                    recognizeText(InputImage.fromFilePath(context, it))
                        .addOnSuccessListener { task ->
                            Log.i("흥수", task.text)
                            navToResult(task.text)
                        }
                }
            }
        })
    return value
}
