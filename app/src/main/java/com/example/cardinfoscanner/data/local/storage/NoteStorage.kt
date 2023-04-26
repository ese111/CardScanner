package com.example.cardinfoscanner.data.local.storage

import com.example.cardinfoscanner.data.local.datastore.NoteDataStore
import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteStorage @Inject constructor(
    private val noteDataStore: NoteDataStore
) {

    fun getNoteList() = noteDataStore.getNoteList()

    suspend fun setNoteList(json: String) = noteDataStore.setNoteList(json)
}