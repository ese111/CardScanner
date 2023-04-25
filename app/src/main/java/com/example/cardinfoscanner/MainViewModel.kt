package com.example.cardinfoscanner

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<Any>(replay = 0)
    val event = _event.asSharedFlow()

    fun putEvent(data: Any) {
        _event.tryEmit(data)
    }

    inline fun <reified T> subscribe(): Flow<T> {
        return event.filter { it is T }.map { it as T }
    }
}