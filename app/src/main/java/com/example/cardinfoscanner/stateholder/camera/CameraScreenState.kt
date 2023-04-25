package com.example.cardinfoscanner.stateholder.camera

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState

@Stable
class CameraScreenState(
    val uiState: BaseUiState,
    var value: MutableState<String>,
    val title: MutableState<String>
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberCameraScreenState(
    uiState: BaseUiState = rememberUiState(),
    value: MutableState<String> = remember{ mutableStateOf("") },
    title: MutableState<String> = remember { mutableStateOf("") }
): CameraScreenState = remember(uiState, value, title) {
    CameraScreenState(
        uiState = uiState,
        value = value,
        title = title
    )
}