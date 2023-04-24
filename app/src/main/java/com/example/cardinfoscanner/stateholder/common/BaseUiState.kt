package com.example.cardinfoscanner.stateholder.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalComposeUiApi::class)
class BaseUiState(
    val context: Context,
    val scope: CoroutineScope,
    val lifecycleOwner: LifecycleOwner,
    val keyboardController: SoftwareKeyboardController?
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberUiState(
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) = remember(
    context, scope, lifecycleOwner, keyboardController
) {
    BaseUiState(
        context = context,
        scope = scope,
        lifecycleOwner = lifecycleOwner,
        keyboardController = keyboardController
    )
}