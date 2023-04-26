package com.example.cardinfoscanner.data.local.repository

import com.example.cardinfoscanner.data.base.NoteDataSource
import com.example.cardinfoscanner.data.base.NoteRepository
import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class LocalNoteRepository @Inject constructor(
    private val noteDataSource: NoteDataSource
): NoteRepository {
    override fun getNotList(): Flow<List<Note>> = noteDataSource.getNoteList().map { pref ->
        Timber.tag("AppTest").d("asMap : ${pref.asMap().values}")
        val flow = pref.asMap().values.toList().map { item ->
            Timber.tag("AppTest").d("getNotList : ${item}")
            val list = Json.decodeFromString<List<Note>>(item.toString())
            list
        }
        flow.first()
    }

    override suspend fun setNoteList(list: List<Note>) {
        noteDataSource.setNoteList(Json.encodeToString(list))
    }

}