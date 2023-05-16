package com.example.cardinfoscanner.stateholder.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Stable
class TopBarDropMenuState(
    val dropMenuState: MutableState<Boolean>
) {
    val openDropMenu = { dropMenuState.value = true }

    val closeDropMenu = { dropMenuState.value = false }
}

@Composable
fun rememberDropMenuState(
    dropMenuState: MutableState<Boolean> = remember { mutableStateOf(false) }
) = remember(dropMenuState) {
    TopBarDropMenuState(
        dropMenuState = dropMenuState
    )
}