package com.example.cardinfoscanner.ui.camera

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.cardinfoscanner.util.CameraUtil

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
internal fun CameraPreView(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    cameraUtil: CameraUtil
) {
    AndroidView(
        factory = {
            cameraUtil.onBindScannerPreview(
                lifecycleOwner = lifecycleOwner
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun CameraPreview() {
    CameraButton {}
}