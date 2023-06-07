package com.example.cardinfoscanner.data.local.storage

import com.example.cardinfoscanner.data.local.datastore.NoteDataStore
import com.example.cardinfoscanner.data.local.model.Note
import com.example.cardinfoscanner.ui.note.detail.NoteDetailUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private var originListCache: Map<Long, Note> = emptyMap()
    private var lastIdCache: Long = -1

    val noteMap: StateFlow<Map<Long, Note>> = noteDataStore.
    getNoteList().map { pref ->
        val map = mutableMapOf<Long, Note>()
        pref.asMap().values.map { item ->
            Timber.tag("AppTest").d("getNotList : $item")
            Json.decodeFromString<List<Note>>(item.toString())
        }.forEach {
            it.forEach { note ->
                map[note.id] = note
            }
        }
        map.keys.maxOrNull()?.let { lastIdCache = it }
        map
    }.stateIn(scope = CoroutineScope(Job()), started = SharingStarted.Lazily, initialValue = emptyMap())

    suspend fun setNoteList(note: Note) {
        val map = mutableMapOf<Long, Note>()
        map.putAll(noteMap.value)
        val newNote = note.copy(id = ++lastIdCache)
        map[newNote.id] = newNote
        Timber.tag("AppTest").d("setNotesList : $map")
        noteDataStore.setNoteList(Json.encodeToString(map.values.toList()))
    }

    suspend fun removeNote(note: Note) {
        val map = mutableMapOf<Long, Note>()
        setCachedOriginList()
        map.putAll(noteMap.value)
        map.remove(note.id)
        Timber.tag("AppTest").d("removeNote : $map")
        noteDataStore.setNoteList(Json.encodeToString(map.values.toList()))
    }

    suspend fun removeAllNote(list: List<Note>) {
        val map = mutableMapOf<Long, Note>()
        setCachedOriginList()
        map.putAll(noteMap.value)
        list.forEach { note ->
            map.remove(note.id)
        }
        Timber.tag("AppTest").d("removeNote : $map")
        noteDataStore.setNoteList(Json.encodeToString(map.values.toList()))
    }

    suspend fun saveNote(note: Note) {
        val map = mutableMapOf<Long, Note>()
        setCachedOriginList()
        map.putAll(noteMap.value)
        map[note.id] = note
        noteDataStore.setNoteList(Json.encodeToString(map.values.toList()))
    }

    private fun setCachedOriginList() {
        originListCache = noteMap.value
    }

    suspend fun cancelRemove() = noteDataStore.setNoteList(Json.encodeToString(originListCache.values.toList()))

    fun getNoteDetail(id: Long): NoteDetailUiState {
        val result = noteMap.value[id]
        return when(result != null) {
            true -> NoteDetailUiState.Success(result)
            false -> NoteDetailUiState.Loading
        }
    }

}