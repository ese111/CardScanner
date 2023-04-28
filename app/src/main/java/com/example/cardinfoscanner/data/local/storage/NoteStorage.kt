package com.example.cardinfoscanner.data.local.storage

import androidx.lifecycle.viewModelScope
import com.example.cardinfoscanner.data.local.datastore.NoteDataStore
import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteStorage @Inject constructor(
    private val noteDataStore: NoteDataStore
) {
    val noteList: StateFlow<List<Note>> = noteDataStore.
    getNoteList().map { pref ->
        val flow = pref.asMap().values.toList().map { item ->
            Timber.tag("AppTest").d("getNotList : $item")
            val list = Json.decodeFromString<List<Note>>(item.toString())
            list
        }
        flow.first()
    }.stateIn(scope = CoroutineScope(Job()), started = SharingStarted.Lazily, initialValue = emptyList())

    suspend fun setNoteList(note: Note) {
        val list = mutableListOf<Note>()
        list.addAll(noteList.value)
        val newNote = note.copy(id = list.size.toLong())
        list.add(newNote)
        Timber.tag("AppTest").d("setNotesList : $list")
        noteDataStore.setNoteList(Json.encodeToString(list))
    }
}