package com.example.cardinfoscanner.ui.permission

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.navigateSingleTopTo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresCameraPermission(
    navController: NavHostController
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    if (cameraPermissionState.hasPermission) {
        Text("Camera permission Granted")
        navController.navigateSingleTopTo(Destination.cameraRoute)
    } else {
        Column {
            val textToShow = if (cameraPermissionState.shouldShowRationale) {
                navController.navigateSingleTopTo(Destination.cameraRoute)
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