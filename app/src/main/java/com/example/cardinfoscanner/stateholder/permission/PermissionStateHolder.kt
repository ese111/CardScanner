package com.example.cardinfoscanner.stateholder.permission

import android.Manifest
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Stable
class PermissionScreenState(
    val sheetState: SheetState,
    val permissionState: PermissionState,
    val baseUiState: BaseUiState
) {
    val launchPermissionRequest = permissionState::launchPermissionRequest
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun rememberPermissionScreenState(
    sheetState: SheetState = rememberModalBottomSheetState(),
    permissionState: PermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA),
    baseUiState: BaseUiState = rememberUiState()
) = remember(permissionState, baseUiState) {
    PermissionScreenState(
        sheetState = sheetState,
        permissionState = permissionState,
        baseUiState = baseUiState
    )
}