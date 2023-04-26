package com.example.cardinfoscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.stateholder.note.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableStateFlow<Any?>(null)
    val event = _event.asSharedFlow()

    fun putEvent(data: Any) {
        Timber.tag("AppTest").d("data : ${data is Note} $data")
        viewModelScope.launch {
            _event.emit(data as Note)
        }
    }

    inline fun <reified T> subscribe(): Flow<T> {
        return event.filter { it is T }.map { it as T }
    }
}