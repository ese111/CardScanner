package com.example.cardinfoscanner.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cardinfoscanner.ui.result.ResultScreen

@Stable
data class ResultState(
    val scanText: MutableState<String>
)


@Composable
fun rememberResultState(
    scanText: MutableState<String> = remember { mutableStateOf("") },
) = remember(scanText) {
    ResultState(
        scanText = scanText
    )
}