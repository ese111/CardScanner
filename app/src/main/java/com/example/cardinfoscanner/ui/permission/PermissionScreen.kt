package com.example.cardinfoscanner.ui.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardinfoscanner.stateholder.permission.PermissionScreenState
import com.example.cardinfoscanner.stateholder.permission.rememberPermissionScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraPermissionBottomSheet(
    state: PermissionScreenState = rememberPermissionScreenState(),
    onDismissRequest: () -> Unit = {},
    moveToNext: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            state.baseUiState.scope.launch {
                state.sheetState.hide()
            }.invokeOnCompletion {
                onDismissRequest()
            }
        },
        sheetState = state.sheetState
    ) {
        Column (
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            PermissionTitle(state.permissionState.status.shouldShowRationale)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            PermissionButton(
                isGranted = state.permissionState.status.isGranted,
                context = state.baseUiState.context,
                shouldShowRationale = state.permissionState.status.shouldShowRationale,
                moveToNext = moveToNext,
                launchPermissionRequest = state.launchPermissionRequest
            )
        }
    }
}

@Composable
private fun PermissionTitle(shouldShowRationale: Boolean) {
    Text(
        text = if (shouldShowRationale) {
            "카메라 권한이 중요합니다.\n카메라 권한을 허가해주세요!"
        } else {
            "카메라 권한을 허용해주셔야\n유용한 앱사용이 가능합니다."
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
    CameraPermissionBottomSheet {}
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