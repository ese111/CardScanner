package com.example.cardinfoscanner.stateholder.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Stable
class TextFieldState(
    val value: MutableState<String>,
    val isFocus: MutableState<Boolean>
) {
    val onChangeFocus: (Boolean) -> Unit = { hasFocus ->
        isFocus.value = hasFocus
    }
    val onTextChange: (String) -> Unit = { str ->
        value.value = str
    }
}

@Composable
fun rememberTextFieldState(
    value: MutableState<String> = remember { mutableStateOf("") },
    isFocus: MutableState<Boolean> = remember { mutableStateOf(false) }
) = remember(value, isFocus) {
    TextFieldState(
        value = value,
        isFocus = isFocus
    )
}

