package com.example.cardinfoscanner

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) {

    private val _event = MutableStateFlow<Any?>(null)
    val event = _event.asStateFlow()

    fun putEvent(data: Any) {
        _event.value = data
    }

}