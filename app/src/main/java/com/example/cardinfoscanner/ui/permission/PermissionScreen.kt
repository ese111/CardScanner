package com.example.cardinfoscanner.ui.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresCameraPermission(
    moveToNext: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                text = "Permission"
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(12.dp)
        ){
            PermissionTitle(cameraPermissionState.status.shouldShowRationale)
            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
            PermissionButton(
                isGranted = cameraPermissionState.status.isGranted,
                context = context,
                shouldShowRationale = cameraPermissionState.status.shouldShowRationale,
                moveToNext = moveToNext,
                launchPermissionRequest = cameraPermissionState::launchPermissionRequest
            )
        }
    }
}

@Composable
private fun PermissionTitle(shouldShowRationale: Boolean) {
    Text(
        text = if (shouldShowRationale) {
            "카메라 권한이 중요합니다. 카메라 권한을 허가해주세요!"
        } else {
            "카메라 권한을 허용해주셔야 유용한 앱사용이 가능합니다."
        }
    )
}

@Composable
private fun PermissionButton(
    isGranted: Boolean = false,
    context: Context = LocalContext.current,
    shouldShowRationale: Boolean = false,
    moveToNext: () -> Unit = {},
    launchPermissionRequest: () -> Unit = {}
) {
    Button(
        onClick = {
            if (isGranted) {
                moveToNext()
                return@Button
            }
            if (shouldShowRationale) {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + context.packageName)
                    context.startActivity(this)
                }
                return@Button
            }
            launchPermissionRequest()
        }
    ) {
        Text("권한 허가")
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionScreenPreview() {
    FeatureThatRequiresCameraPermission {}
}

@Preview(showBackground = true)
@Composable
private fun PermissionPreview() {
    PermissionTitle(true)
}

@Preview(showBackground = true)
@Composable
private fun PermissionButtonPreview() {
    PermissionButton()
}