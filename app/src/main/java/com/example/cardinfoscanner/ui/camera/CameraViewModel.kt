package com.example.cardinfoscanner.ui.camera

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(): ViewModel() {

    fun getCameraExecutor(): ExecutorService = Executors.newSingleThreadExecutor()

    fun getImageCapture() = ImageCapture.Builder().build()
}