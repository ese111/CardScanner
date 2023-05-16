package com.example.cardinfoscanner.stateholder.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Stable
class DialogState(
    val dialogState: MutableState<Boolean>
) {
    val onChangeDialogState: (Boolean) -> Unit = { state ->
        dialogState.value = state
    }
}

@Composable
fun rememberDialogState(
    dialogState: MutableState<Boolean> = mutableStateOf(false),
    isFocus: MutableState<Boolean> = mutableStateOf(false)
) = remember(dialogState) {
    DialogState(dialogState)
}