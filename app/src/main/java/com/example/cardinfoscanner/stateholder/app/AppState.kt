package com.example.cardinfoscanner.stateholder.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState

@Stable
class AppState(
    val navHostController: NavHostController,
    val uiState: BaseUiState
) {
    var bottomBarVisible: Boolean by mutableStateOf(false)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController(),
    uiState: BaseUiState = rememberUiState()
) = remember(navHostController, uiState) {
    AppState(
        navHostController = navHostController,
        uiState = uiState
    )
}