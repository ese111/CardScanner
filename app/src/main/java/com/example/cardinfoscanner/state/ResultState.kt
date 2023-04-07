package com.example.cardinfoscanner.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.example.cardinfoscanner.ui.result.ResultScreen

@Stable
data class ResultState(
    val uiState: String
)

@Composable
fun rememberResultState(
    uiState: String
) = remember(uiState) {
    ResultState(uiState = uiState)
}